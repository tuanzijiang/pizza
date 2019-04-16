import { ResultType } from '../common';

export interface FetchPhoneReq {
  orderId: string;
}

export interface FetchPhoneResp {
  resultType: ResultType;
  deliverymanPhone: string;
  shopPhone: string;
  servicePhone: string;
}
