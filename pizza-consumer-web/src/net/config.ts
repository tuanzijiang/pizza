import { Command } from './command';

export const reqProto = {
  [Command.FETCH_USER]: 'user.FetchUserReq',
  [Command.FETCH_MENU]: 'order.FetchMenuReq',
  [Command.FETCH_ORDERS]: 'order.FetchOrdersReq',
  [Command.UPDATE_ORDER]: 'order.UpdateOrderReq',
  [Command.FETCH_ADDRESS]: 'address.FetchAddressReq',
  [Command.LOGIN]: 'user.LoginReq',
  [Command.REGISTER]: 'user.SignUpReq',
  [Command.SEND_VERIFICATION]: 'user.SendVerificationReq',
  [Command.SET_PW]: 'user.SetPWReq',
  [Command.ADD_ADDRESS]: 'address.AddAddressReq',
  [Command.UPDATE_ADDRESS]: 'address.UpdateAddressReq',
};

export const respProto = {
  [Command.FETCH_USER]: 'user.FetchUserResp',
  [Command.FETCH_MENU]: 'order.FetchMenuResp',
  [Command.FETCH_ORDERS]: 'order.FetchOrdersResp',
  [Command.UPDATE_ORDER]: 'order.UpdateOrderResp',
  [Command.FETCH_ADDRESS]: 'address.FetchAddressResp',
  [Command.LOGIN]: 'user.LoginResp',
  [Command.REGISTER]: 'user.SignUpResp',
  [Command.SEND_VERIFICATION]: 'user.SendVerificationResp',
  [Command.SET_PW]: 'user.SetPWResp',
  [Command.ADD_ADDRESS]: 'address.AddAddressResp',
  [Command.UPDATE_ADDRESS]: 'address.UpdateAddressResp',
};

export const reqUrl = {
  [Command.FETCH_USER]: 'fetch_user',
  [Command.FETCH_MENU]: 'fetch_menu',
  [Command.FETCH_ORDERS]: 'fetch_orders',
  [Command.UPDATE_ORDER]: 'update_order',
  [Command.FETCH_ADDRESS]: 'fetch_address',
  [Command.LOGIN]: 'fetch_loginStatus',
  [Command.REGISTER]: 'add_user',
  [Command.SEND_VERIFICATION]: 'send_verification',
  [Command.SET_PW]: 'set_pw',
  [Command.ADD_ADDRESS]: 'add_address',
  [Command.UPDATE_ADDRESS]: 'update_address',
};
