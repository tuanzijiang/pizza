import { ResultType, Address } from '../common';

export interface FetchAddressReq {
  userId: number;
}

export interface FetchAddressResp {
  resultType: ResultType;
  addresses: Address[];
}
