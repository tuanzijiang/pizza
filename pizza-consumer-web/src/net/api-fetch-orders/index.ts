import { ResultType, Order, OrderStatus } from '../common';

export interface FetchOrdersReq {
  userId: number;
  lastOrderId: string;
  num: number;
  status: OrderStatus;
}

export interface FetchOrdersResp {
  resultType: ResultType;
  orders: Order[];
}
