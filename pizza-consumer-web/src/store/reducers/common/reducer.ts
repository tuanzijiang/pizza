import {
  UPDATE_TOAST, UPDATE_CART_ID,
} from './action';

const initState = {
  toast_text: '',
  toast_isOpen: false,
  cart_id: '',
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
    case UPDATE_CART_ID: {
      return {
        ...state,
        cart_id: action.cartId,
      };
    }
    default:
      return state;
  }
};
