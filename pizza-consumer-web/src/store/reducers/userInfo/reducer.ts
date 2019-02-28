import { CHANGE_LOGIN_STATE } from './action';

const initState = {
  isLogin: false,
};

export default (state = initState, action: any) => {
  switch (action.type) {
    case CHANGE_LOGIN_STATE: {
      return {
        ...state,
        isLogin: action.isLogin,
      };
    }
    default:
      return state;
  }
};
