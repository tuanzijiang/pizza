import { OrderWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_ORDER = Symbol('UPDATE_ORDER');

export const UPDATE_ORDERS = Symbol('UPDATE_ORDERES');

export const UPDATE_ORDER_NUM = Symbol('UPDATE_ORDER_NUM');

/**
 * action creator
 */
const updateOrder = (order: OrderWeakSchema) => ({
  order,
  type: UPDATE_ORDER,
});

const updateOrders = (orders: {
  [key: string]: OrderWeakSchema,
}) => ({
  orders,
  type: UPDATE_ORDERS,
});

const updateOrderNum = (orderId: string, pizzaId: number, count: number) => ({
  orderId,
  pizzaId,
  count,
  type: UPDATE_ORDER_NUM,
});

export default {
  updateOrder,
  updateOrders,
  updateOrderNum,
};
