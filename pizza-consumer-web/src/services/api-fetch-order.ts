import net, { Command } from '@net/base';
import { Order } from '@entity/Order';
import { OrderStatusSchema } from '@entity/Order/schema';
import { Address } from '@entity/Address';
import { Pizza } from '@entity/Pizza';
import store from '@store/index';
import { entity, pageMobile, pagePc } from '@store/action';
import { FetchOrderReq, FetchOrderResp } from '@src/net/api-fetch-order';
import { AddressWeakSchema } from '@src/entity/schema';

export const fetchOrderApi = async (param: FetchOrderReq) => {
  const resp = await net.request(Command.FETCH_ORDER, param);
  const { resultType, order: responseOrder } = resp as FetchOrderResp;

  if (resultType) {
    const updateOrders: {
      [key: string]: Order,
    } = {};
    const updateAddress: {
      [key: number]: Address,
    } = {};
    let updatePizzas: Pizza[] = [];
    const currOrdersIds: string[] = [];

    [responseOrder].forEach(order => {
      const { address, pizzas } = order;
      // @ts-ignore
      const addressId = Address.fromJS(address).id;

      const pizzasInfo = pizzas.reduce((prev: any, curr) => {
        prev[0] = prev[0].concat(curr.id);
        prev[1][curr.id] = curr.count;
        prev[2] = prev[2].concat(Pizza.fromJS(curr));
        return prev;
      }, [[], {}, []]);

      const currOrder = Order.fromJS({
        id: order.id,
        startTime: order.startTime,
        status: (order.status as unknown) as OrderStatusSchema,
        num: pizzasInfo[1],
        pizzas: pizzasInfo[0],
        address: addressId,
      });

      currOrdersIds.push(order.id);

      updatePizzas = updatePizzas.concat(pizzasInfo[2]);
      updateOrders[order.id] = currOrder;
      updateAddress[addressId] = Address.fromJS(
        order.address as unknown as AddressWeakSchema,
      );
    });

    store.dispatch(entity.pizzas.updatePizzas(updatePizzas));
    store.dispatch(entity.addresses.updateAddresses(updateAddress));
    store.dispatch(entity.orders.updateOrders(updateOrders));
    store.dispatch(pageMobile.main.updateOrdersId(currOrdersIds));
    store.dispatch(pagePc.main.updateOrdersId(currOrdersIds));
  }

  return resp;
};
