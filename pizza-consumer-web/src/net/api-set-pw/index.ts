import { ResultType } from '../common';

export interface SetPWReq {
  account: string;
  password: string;
  varification: string;
}

export interface SetPWResp {
  resultType: ResultType;
}
