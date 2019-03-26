import { ResultType, LoginType, User } from '../common';

export interface LoginReq {
  type: LoginType;
  account: string;
  password: string;
}

export interface LoginResp {
  resultType: ResultType;
  user: User;
}
