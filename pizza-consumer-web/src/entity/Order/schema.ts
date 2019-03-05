export enum OrderStatusSchema {
  CART = 0,
  UNPAID = 1,
  PAID = 2,
  CANCEL_CHECKING = 3,
  CANCELED = 4,
  CANCEL_FAILED = 5,
  DELIVERING = 6,
  RECEIVED = 7,
  FINISH = 8,
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
