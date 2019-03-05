import { Command } from './command';

export const reqProto = {
  [Command.FETCH_USER]: 'user.FetchUserReq',
  [Command.FETCH_MENU]: 'order.FetchMenuReq',
};

export const respProto = {
  [Command.FETCH_USER]: 'user.FetchUserResp',
  [Command.FETCH_MENU]: 'order.FetchMenuResp',
};

export const reqUrl = {
  [Command.FETCH_USER]: 'fetch_user',
  [Command.FETCH_MENU]: 'fetch_menu',
};
