import net, { Command } from '@net/base';
import { PayOrderReq, PayOrderResp } from '@src/net/api-pay-order';
import store from '@store/index';
import commonActionCreator from '@store/reducers/common/action';

export const payOrderApi = async (param: PayOrderReq) => {
  const resp = await net.request(Command.PAY_ORDER, param);
  const { resultType, form } = resp as PayOrderResp;

  if (resultType) {
    store.dispatch(commonActionCreator.updatePayForm(form));

    const formWrapper = document.createElement('div');
    formWrapper.innerHTML = form;
    document.body.append(formWrapper);
    setTimeout(() => {
      Array.prototype.forEach.apply(formWrapper.children, [(v: HTMLElement) => {
        if (v.tagName.toLowerCase() === 'form') {
          const formEl = v as HTMLFormElement;
          formEl.submit();
        }
      }]);
    }, 500);
    return true;
  }
  return false;
};
