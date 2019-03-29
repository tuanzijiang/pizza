import { ResultType } from '../common';

export interface SendOrderReq {
  orderId: string;
  userAddressId: number;
}

export interface SendOrderResp {
  resultType: ResultType;
}
