import { Command } from './Command';

export const reqProto = {
  [Command.FETCH_USER]: 'user.FetchUserReq',
  [Command.FETCH_MENU]: 'order.FetchMenuReq',
  [Command.FETCH_ORDERS]: 'order.FetchOrdersReq',
  [Command.UPDATE_ORDER]: 'order.UpdateOrderReq',
  [Command.FETCH_ADDRESS]: 'address.FetchAddressReq',
};

export const respProto = {
  [Command.FETCH_USER]: 'user.FetchUserResp',
  [Command.FETCH_MENU]: 'order.FetchMenuResp',
  [Command.FETCH_ORDERS]: 'order.FetchOrdersResp',
  [Command.UPDATE_ORDER]: 'order.UpdateOrderResp',
  [Command.FETCH_ADDRESS]: 'address.FetchAddressResp',
};

export const reqUrl = {
  [Command.FETCH_USER]: 'fetch_user',
  [Command.FETCH_MENU]: 'fetch_menu',
  [Command.FETCH_ORDERS]: 'fetch_orders',
  [Command.UPDATE_ORDER]: 'update_order',
  [Command.FETCH_ADDRESS]: 'fetch_address',
};
