import commonActionCreator from '@store/reducers/common/action';

let openToastTimer: any = null;
const TOAST_TIME = 1000;

export const openToast = (text: string) => {
  if (openToastTimer) {
    clearInterval(openToastTimer);
    openToastTimer = null;
  }

  __STORE__.dispatch(
    commonActionCreator.updateToast(text, true),
  );
  openToastTimer = setTimeout(() => {
    __STORE__.dispatch(
      commonActionCreator.updateToast('', false),
    );
    openToastTimer = null;
  }, TOAST_TIME);
};
