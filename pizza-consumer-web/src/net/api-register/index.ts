import { ResultType, User } from '../common';

export interface RegisterReq {
  phone?: string;
  password?: string;
  email?: string;
}

export interface RegisterResp {
  resultType: ResultType;
  user: User;
}
