import net, { Command } from '@net/base';
import { CancelOrderReq, CancelOrderResp } from '@src/net/api-cancel-order';

export const cancelOrdersApi = async (param: CancelOrderReq) => {
  const resp = await net.request(Command.CANCEL_ORDER, param);
  const { resultType } = resp as CancelOrderResp;

  if (resultType) {
  }

  return resp;
};
