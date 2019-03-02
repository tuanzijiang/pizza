import { FetchUserReq, FetchUserResp } from './user';

export enum Command {
  // user
  FETCH_USER = 'FETCH_USER',
}

export type CommandReq<T extends Command> =
  T extends Command.FETCH_USER ? [FetchUserReq] : [];

export type CommandResp<T extends Command> =
  T extends Command.FETCH_USER ? FetchUserResp : void;
