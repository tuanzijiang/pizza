import { ResultType, Order } from '../common';

export interface FetchOrderReq {
  userId: number;
  orderId: string;
}

export interface FetchOrderResp {
  resultType: ResultType;
  order: Order;
}
