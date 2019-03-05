import {
  UPDATE_NAV_IDX,
} from './action';

const initState = {
  navIdx: 0,
};

export default (state = initState, action: any) => {
  switch (action.type) {
    case UPDATE_NAV_IDX: {
      return {
        ...state,
        navIdx: action.navIdx,
      };
    }
    default:
      return state;
  }
};
