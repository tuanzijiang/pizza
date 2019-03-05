import { OrderSchema, OrderStatusSchema, OrderWeakSchema } from './schema';

export const CART_ORDER_ID = '0000';

export class Order implements OrderSchema {
  public id: string = '';
  public startTime: number = 0;
  public pizzas: number[] = [];
  public address: number = 0;
  public status: OrderStatusSchema = OrderStatusSchema.CART;
  public num: {
    [pizza_id: number]: number;
  } = {};

  constructor(order?: OrderWeakSchema) {
    if (order) {
      this.id = order.id || this.id;
      this.startTime = order.startTime || this.startTime;
      this.pizzas = order.pizzas || this.pizzas;
      this.address = order.address || this.address;
      this.status = order.status || this.status;
      this.num = order.num || this.num;
    }
  }

  update(order: OrderWeakSchema, onlyModified?: boolean) {
    if (onlyModified) {
      this.id = order.id || this.id;
      this.startTime = order.startTime || this.startTime;
      this.pizzas = order.pizzas || this.pizzas;
      this.address = order.address || this.address;
      this.status = order.status || this.status;
      this.num = order.num || this.num;
      return this;
    } else {
      const newOrder = Order.fromJS();
      newOrder.id = order.id || this.id;
      newOrder.startTime = order.startTime || this.startTime;
      newOrder.pizzas = order.pizzas || this.pizzas;
      newOrder.address = order.address || this.address;
      newOrder.status = order.status || this.status;
      newOrder.num = order.num || this.num;
      return newOrder;
    }
  }

  static fromJS(order?: OrderWeakSchema) {
    return new Order(order);
  }
}
