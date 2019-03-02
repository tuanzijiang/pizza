export enum OrderStatusSchema {
  CART = 0,
  UNPAID = 1,
  PAID = 2,
  RECEIVED = 3,
  TRANSPORT = 4,
  FINISH = 5,
}

export interface OrderSchema {
  id: string;
  startTime: number;
  pizzas: string[];
  address: number;
  status: OrderStatusSchema;
  phone: string;
  num: {
    [pizza_id: number]: number;
  };
}
