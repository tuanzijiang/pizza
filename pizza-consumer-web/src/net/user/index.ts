import { UserSchema } from '@entity/schema';

export enum ResultType {
  FAILURE = 0,
  SUCCESS = 1,
}

export interface FetchUserReq {
  userId: number;
}

export interface FetchUserResp {
  resultType: ResultType;
  user: UserSchema;
}
