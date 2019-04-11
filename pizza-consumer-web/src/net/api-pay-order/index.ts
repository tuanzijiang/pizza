import { ResultType } from '../common';

export enum PayType {
  MOBILE = 0,
  PC = 1,
}

export interface PayOrderReq {
  orderId: string;
  totalPrice: number;
  type: PayType;
}

export interface PayOrderResp {
  resultType: ResultType;
  form: string;
}
