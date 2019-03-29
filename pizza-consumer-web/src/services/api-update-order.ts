import net, { Command } from '@net/base';
import { UpdateOrderReq, UpdateOrderResp } from '@src/net/api-update-order';
import store from '@store/index';
import { entity } from '@store/action';
import { CART_ORDER_ID } from '@src/entity/Order';

export const updateOrderApi = async (param: UpdateOrderReq) => {
  const { pizzaId, orderId, count } = param;
  const resp = await net.request(Command.UPDATE_ORDER, param);
  const { resultType } = resp as UpdateOrderResp;

  if (resultType) {
    store.dispatch(entity.orders.updateOrderNum(CART_ORDER_ID, pizzaId, count));
  }

  return resp;
};
