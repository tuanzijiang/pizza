import { FetchUserReq, FetchUserResp } from './api-fetch-user';
import { FetchMenuReq, FetchMenuResp } from './api-fetch-menu';
import { FetchOrdersReq, FetchOrdersResp } from './api-fetch-orders';
import { FetchAddressReq, FetchAddressResp } from './api-fetch-address';
import { LoginReq, LoginResp } from './api-login';
import { RegisterReq, RegisterResp } from './api-register';
import { SendVerificationReq, SendVerificationResp } from './api-send-verification';
import { UpdateOrderReq, UpdateOrderResp } from './api-update-order';
import { SetPWReq, SetPWResp } from './api-set-pw';
import { AddAddressReq, AddAddressResp } from './api-add-address';
import { UpdateAddressReq, UpdateAddressResp } from './api-update-address';
import { FetchOrderReq, FetchOrderResp } from './api-fetch-order';
import { SendOrderReq, SendOrderResp } from './api-send-order';
import { CancelOrderReq, CancelOrderResp } from './api-cancel-order';

export enum Command {
  // user
  FETCH_USER = 'FETCH_USER',
  LOGIN = 'LOGIN',
  REGISTER = 'REGISTER',
  SEND_VERIFICATION = 'SEND_VERIFICATION',
  SET_PW = 'SET_PW',

  // menu
  FETCH_MENU = 'FETCH_MENU',

  // order
  FETCH_ORDERS = 'FETCH_ORDERS',
  UPDATE_ORDER = 'UPDATE_ORDER',
  FETCH_ORDER = 'FETCH_ORDER',
  SEND_ORDER = 'SEND_ORDER',
  CANCEL_ORDER = 'CANCEL_ORDER',

  // address
  FETCH_ADDRESS = 'FETCH_ADDRESS',
  UPDATE_ADDRESS = 'UPDATE_ADDRESS',
  ADD_ADDRESS = 'ADD_ADDRESS',
}

export type CommandReq<T extends Command> =
  T extends Command.FETCH_USER ? [FetchUserReq] :
  T extends Command.FETCH_MENU ? [FetchMenuReq] :
  T extends Command.FETCH_ORDERS ? [FetchOrdersReq] :
  T extends Command.UPDATE_ORDER ? [UpdateOrderReq] :
  T extends Command.FETCH_ADDRESS ? [FetchAddressReq] :
  T extends Command.LOGIN ? [LoginReq] :
  T extends Command.REGISTER ? [RegisterReq] :
  T extends Command.SEND_VERIFICATION ? [SendVerificationReq] :
  T extends Command.SET_PW? [SetPWReq] :
  T extends Command.ADD_ADDRESS? [AddAddressReq] :
  T extends Command.UPDATE_ADDRESS? [UpdateAddressReq] :
  T extends Command.FETCH_ORDER? [FetchOrderReq] :
  T extends Command.SEND_ORDER? [SendOrderReq] :
  T extends Command.CANCEL_ORDER? [CancelOrderReq] :
  [];

export type CommandResp<T extends Command> =
  T extends Command.FETCH_USER ? FetchUserResp :
  T extends Command.FETCH_MENU ? FetchMenuResp :
  T extends Command.FETCH_ORDERS ? FetchOrdersResp :
  T extends Command.UPDATE_ORDER ? UpdateOrderResp :
  T extends Command.FETCH_ADDRESS ? FetchAddressResp :
  T extends Command.LOGIN ? LoginResp :
  T extends Command.REGISTER? RegisterResp :
  T extends Command.SEND_VERIFICATION ? SendVerificationResp :
  T extends Command.SET_PW? SetPWResp :
  T extends Command.ADD_ADDRESS? AddAddressResp:
  T extends Command.UPDATE_ADDRESS? UpdateAddressResp:
  T extends Command.FETCH_ORDER? FetchOrderResp:
  T extends Command.SEND_ORDER? SendOrderResp:
  T extends Command.CANCEL_ORDER? CancelOrderResp:
  void;
