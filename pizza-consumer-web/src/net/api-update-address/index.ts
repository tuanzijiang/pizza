import { ResultType, Address } from '../common';

export interface UpdateAddressReq {
  userId: number;
  address: Address;
}

export interface UpdateAddressResp {
  resultType: ResultType;
}
