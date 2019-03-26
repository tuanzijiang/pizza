import {
  UPDATE_TOAST,
} from './action';

const initState = {
  toast_text: '',
  toast_isOpen: false,
};

export default (state = initState, action: any) => {
  switch (action.type) {
    case UPDATE_TOAST: {
      return {
        ...state,
        toast_isOpen: action.isOpen,
        toast_text: action.text,
      };
    }
    default:
      return state;
  }
};
