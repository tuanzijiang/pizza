import { FetchUserReq, FetchUserResp } from './user';

export enum Command {
  // user
  FETCH_USER = 'FETCH_USER',
}

export type CommandReq<T extends Command> =
  T extends Command.FETCH_USER ? [FetchUserReq] : [];

export type CommandResp<T extends Command> =
  T extends Command.FETCH_USER ? FetchUserResp : void;

export const reqProto = {
  [Command.FETCH_USER]: 'user.FetchUserReq',
};

export const respProto = {
  [Command.FETCH_USER]: 'user.FetchUserResp',
};

export const reqUrl = {
  [Command.FETCH_USER]: 'http://localhost:3000/user',
};
