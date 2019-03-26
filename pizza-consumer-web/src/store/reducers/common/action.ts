/**
 * action type
 */
export const UPDATE_TOAST = Symbol('UPDATE_TOAST');

const updateToast = (text: string, isOpen: boolean) => ({
  text,
  isOpen,
  type: UPDATE_TOAST,
});

export default {
  updateToast,
};
