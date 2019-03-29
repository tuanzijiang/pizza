export enum OrderStatusSchema {
  UNKONWN = 0,
  CART = 1,
  UNPAID = 2,
  PAID = 3,
  CANCEL_CHECKING = 4,
  CANCELED = 5,
  CANCEL_FAILED = 6,
  DELIVERING = 7,
  RECEIVED = 8,
  FINISH = 9,
  WAIT_DELIVERY = 10,
  RECEIVE_FAIL = 11,
}

export interface OrderSchema {
  id: string;
  startTime: number;
  pizzas: number[];
  address: number;
  status: OrderStatusSchema;
  num: {
    [pizza_id: number]: number;
  };
}

export interface OrderWeakSchema {
  id?: string;
  startTime?: number;
  pizzas?: number[];
  address?: number;
  status?: OrderStatusSchema;
  num?: {
    [pizza_id: number]: number;
  };
}

export interface OrderMap {
  [prop: string]: OrderSchema | OrderWeakSchema;
}
