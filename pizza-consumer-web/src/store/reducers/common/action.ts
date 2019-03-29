/**
 * action type
 */
export const UPDATE_TOAST = Symbol('UPDATE_TOAST');

export const UPDATE_CART_ID = Symbol('UPDATE_CART_ID');

const updateToast = (text: string, isOpen: boolean) => ({
  text,
  isOpen,
  type: UPDATE_TOAST,
});

const updateCartId = (cartId: string) => ({
  cartId,
  type: UPDATE_CART_ID,
});

export default {
  updateToast,
  updateCartId,
};
