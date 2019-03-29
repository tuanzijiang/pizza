import net, { Command } from '@net/base';
import { SendOrderReq, SendOrderResp } from '@src/net/api-send-order';

export const sendOrdersApi = async (param: SendOrderReq) => {
  const resp = await net.request(Command.SEND_ORDER, param);
  const { resultType } = resp as SendOrderResp;

  if (resultType) {
    return true;
  }

  return false;
};
