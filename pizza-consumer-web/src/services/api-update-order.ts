import net, { Command } from '@net/base';
import { UpdateOrderReq, UpdateOrderResp } from '@src/net/update-order';
import store from '@store/index';
import { entity } from '@store/action';

export const updateOrderApi = async (param: UpdateOrderReq) => {
  const { pizzaId, orderId, count } = param;
  const resp = await net.request(Command.UPDATE_ORDER, param);
  const { resultType } = resp as UpdateOrderResp;

  if (resultType) {
    store.dispatch(entity.orders.updateOrderNum(orderId, pizzaId, count));
  }

  return resp;
};
