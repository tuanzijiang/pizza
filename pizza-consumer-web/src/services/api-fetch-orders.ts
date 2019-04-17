import net, { Command } from '@net/base';
import { Order } from '@entity/Order';
import { OrderStatusSchema } from '@entity/Order/schema';
import { Address } from '@entity/Address';
import { Pizza } from '@entity/Pizza';
import store from '@store/index';
import { entity, pageMobile, pagePc } from '@store/action';
import { FetchOrdersReq, FetchOrdersResp } from '@src/net/api-fetch-orders';
import { AddressWeakSchema } from '@src/entity/schema';

import { add, OBJECT_STORE_NAMES } from '@utils/db';
import { OrderStatus } from '@src/net/common';

const ALL_STATUS = [
  // OrderStatus.UNKONWN,
  OrderStatus.CANCELED,
  OrderStatus.CANCEL_CHECKING,
  OrderStatus.CANCEL_FAILED,
  // OrderStatus.CART,
  OrderStatus.DELIVERING,
  OrderStatus.FINISH,
  OrderStatus.PAID,
  OrderStatus.RECEIVED,
  OrderStatus.UNPAID,
  OrderStatus.WAIT_DELIVERY,
  OrderStatus.RECEIVE_FAIL,
];

export const fetchOrdersApi = async (param: FetchOrdersReq) => {
  param.status = ALL_STATUS;
  const resp = await net.request(Command.FETCH_ORDERS, param);
  const { resultType, orders } = resp as FetchOrdersResp;

  if (resultType) {
    const updateOrders: {
      [key: string]: Order,
    } = {};
    const updateAddress: {
      [key: number]: Address,
    } = {};
    let updatePizzas: Pizza[] = [];
    const currOrdersIds: string[] = [];

    orders.reverse().forEach(order => {
      const { address, pizzas } = order;
      const addressId = address.id;

      const pizzasInfo = pizzas.reduce((prev: any, curr) => {
        prev[0] = prev[0].concat(curr.id);
        prev[1][curr.id] = curr.count;
        prev[2] = prev[2].concat(Pizza.fromJS(curr));
        return prev;
      }, [[], {}, []]);

      const currOrder = Order.fromJS({
        id: order.id,
        startTime: order.startTime || new Date().valueOf(),
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

    // 插入数据库
    await add(OBJECT_STORE_NAMES.PIZZA, updatePizzas);
    await add(OBJECT_STORE_NAMES.ADDRESS, Object.values(updateAddress));
    await add(OBJECT_STORE_NAMES.ORDER, Object.values(updateOrders));
  }

  return resp;
};
