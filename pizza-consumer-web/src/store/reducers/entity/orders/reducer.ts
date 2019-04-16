import { OrderMap } from '@entity/schema';

import {
  UPDATE_ORDERS, UPDATE_ORDER, UPDATE_ORDER_NUM, CLEAR,
} from './action';

export default (state: OrderMap = {}, action: any) => {
  switch (action.type) {
    case UPDATE_ORDER: {
      const order = action.order;
      if (order.id) {
        return {
          ...state,
          [order.id]: order,
        };
      }
    }
    case UPDATE_ORDERS: {
      const orders = action.orders;
      return {
        ...state,
        ...orders,
      };
    }
    case UPDATE_ORDER_NUM: {
      const {
        orderId,
        pizzaId,
        count,
      } = action;
      const currOrder = state[orderId];
      currOrder.num[pizzaId] = count;
      return {
        ...state,
        [orderId]: {
          ...currOrder,
        },
      };
    }
    case CLEAR: {
      return { };
    }
    default:
      return state;
  }
};
