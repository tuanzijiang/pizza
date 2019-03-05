import { ResultType, User } from '../common';

export interface FetchUserReq {
  userId: number;
}

export interface FetchUserResp {
  resultType: ResultType;
  user: User;
}
