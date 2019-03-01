import { OrderSchema, OrderStatusSchema } from './schema';

export class Order implements OrderSchema {
  public id: string = '';
  public startTime: string = '';
  public pizzas: string[] = [];
  public address: number = 0;
  public status: OrderStatusSchema = OrderStatusSchema.CART;
  public phone: string = '';
  public num: {
    [pizza_id: number]: number;
  } = {};

  constructor(order?: {
    [prop: string]: any,
  }) {
    if (order) {
      this.id = order.id && this.id;
      this.startTime = order.startTime && this.startTime;
      this.pizzas = order.pizzas && this.pizzas;
      this.address = order.address && this.address;
      this.status = order.status && this.status;
      this.phone = order.phone && this.phone;
      this.num = order.num && this.num;
    }
  }

  static fromJS(order?: {
    [prop: string]: any,
  }) {
    return new Order(order);
  }
}
