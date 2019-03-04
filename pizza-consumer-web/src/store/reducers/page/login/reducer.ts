import {
  UPDATE_NAV_IDX,
  UPDATE_VARIFY_TIME,
  UPDATE_SET_VARIFY_TIME,
} from './action';

const initState = {
  navIdx: 0,
  varifyTime: 0,
  setVarifyTime: 0,
};

export default (state = initState, action: any) => {
  switch (action.type) {
    case UPDATE_NAV_IDX: {
      return {
        ...state,
        navIdx: action.navIdx,
      };
    }
    case UPDATE_VARIFY_TIME: {
      return {
        ...state,
        varifyTime: action.varifyTime,
      };
    }
    case UPDATE_SET_VARIFY_TIME: {
      return {
        ...state,
        setVarifyTime: action.varifyTime,
      };
    }
    default:
      return state;
  }
};
