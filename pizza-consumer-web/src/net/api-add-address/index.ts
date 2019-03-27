import { ResultType, Address } from '../common';

export interface AddAddressReq {
  userId: number;
  address: Address;
}

export interface AddAddressResp {
  resultType: ResultType;
}
