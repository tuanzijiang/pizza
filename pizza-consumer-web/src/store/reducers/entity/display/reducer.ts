import {
  ADD_DISPLAY, UPDATE_DISPLAY_ID, UPDATE_DISPLAY, UPDATE_DISPLAY_KEY,
} from './action';
import _ from 'lodash';
import { DisplayInfoEntity as DisplayInfoSchema } from './schema';

export default (state: DisplayInfoSchema = {
  display: {},
  display_order: [],
  curr_display_id: '',
}, action: any) => {
  switch (action.type) {
    case ADD_DISPLAY: {
      return {
        ...state,
        ...action.displayInfo,
      };
    }
    case UPDATE_DISPLAY_ID: {
      return {
        ...state,
        curr_display_id: action.displayId,
      };
    }
    case UPDATE_DISPLAY: {
      return {
        ...state,
        display: action.display,
      };
    }
    case UPDATE_DISPLAY_KEY: {
      const { currKey, rootKey, propName, propValue } = action;
      const isRoot = currKey === rootKey;

      if (isRoot) {
        const newState = _.cloneDeep(state);
        newState.display[rootKey].tag_props[propName] = propValue;
        return newState;
      } else {
        const newState = _.cloneDeep(state);
        const tag_children_idx = newState.display[rootKey].tag_children.reduce(
          (prev, curr, idx) => {
            const { id } = curr;
            return id === currKey ? idx : prev;
          }, -1);
        newState.display[rootKey].tag_children[tag_children_idx].tag_props[propName] = propValue;
        return newState;
      }
    }
    default:
      return state;
  }
};
