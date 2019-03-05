import { FetchUserReq, FetchUserResp } from './fetch-user';
import { FetchMenuReq, FetchMenuResp } from './fetch-menu';

export enum Command {
  // user
  FETCH_USER = 'FETCH_USER',

  // menu
  FETCH_MENU = 'FETCH_MENU',
}

export type CommandReq<T extends Command> =
  T extends Command.FETCH_USER ? [FetchUserReq] :
  T extends Command.FETCH_MENU ? [FetchMenuReq] :
  [];

export type CommandResp<T extends Command> =
  T extends Command.FETCH_USER ? FetchUserResp :
  T extends Command.FETCH_MENU ? FetchMenuResp :
  void;
