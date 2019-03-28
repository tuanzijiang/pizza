import { ResultType, Pizza, Order } from '../common';

export interface FetchMenuReq {
  userId: number;
}

export interface FetchMenuResp {
  resultType: ResultType;
  pizzas: Pizza[];
  cart: Order;
}
