/**
 * action type
 */
export const CHANGE_LOGIN_STATE = Symbol('CHANGE_LOGIN_STATE');

/**
 * action creator
 */
const updateLoginState = (isLogin: boolean) => ({
  isLogin,
  type: CHANGE_LOGIN_STATE,
});

export const userInfo = {
  updateLoginState,
};
