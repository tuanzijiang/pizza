import { ResultType } from '../common';

export interface UpdateOrderReq {
  orderId: string;
  pizzaId: number;
  count: number;
}

export interface UpdateOrderResp {
  resultType: ResultType;
}
