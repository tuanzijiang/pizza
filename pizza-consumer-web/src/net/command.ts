import { FetchUserReq, FetchUserResp } from './fetch-user';
import { FetchMenuReq, FetchMenuResp } from './fetch-menu';
import { FetchOrdersReq, FetchOrdersResp } from './fetch-orders';
import { FetchAddressReq, FetchAddressResp } from './fetch-address';
import { UpdateOrderReq, UpdateOrderResp } from './update-order';

export enum Command {
  // user
  FETCH_USER = 'FETCH_USER',

  // menu
  FETCH_MENU = 'FETCH_MENU',

  // order
  FETCH_ORDERS = 'FETCH_ORDERS',
  UPDATE_ORDER = 'UPDATE_ORDER',

  // address
  FETCH_ADDRESS = 'FETCH_ADDRESS',
}

export type CommandReq<T extends Command> =
  T extends Command.FETCH_USER ? [FetchUserReq] :
  T extends Command.FETCH_MENU ? [FetchMenuReq] :
  T extends Command.FETCH_ORDERS ? [FetchOrdersReq] :
  T extends Command.UPDATE_ORDER ? [UpdateOrderReq] :
  T extends Command.FETCH_ADDRESS ? [FetchAddressReq] :
  [];

export type CommandResp<T extends Command> =
  T extends Command.FETCH_USER ? FetchUserResp :
  T extends Command.FETCH_MENU ? FetchMenuResp :
  T extends Command.FETCH_ORDERS ? FetchOrdersResp :
  T extends Command.UPDATE_ORDER ? UpdateOrderResp :
  T extends Command.FETCH_ADDRESS ? FetchAddressResp :
  void;
