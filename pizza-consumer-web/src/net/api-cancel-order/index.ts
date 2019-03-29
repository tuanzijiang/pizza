import { ResultType } from '../common';

export interface CancelOrderReq {
  orderId: string;
}

export interface CancelOrderResp {
  resultType: ResultType;
}
