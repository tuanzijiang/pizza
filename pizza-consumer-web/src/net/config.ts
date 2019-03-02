import { Command } from './command';

export const reqProto = {
  [Command.FETCH_USER]: 'user.FetchUserReq',
};

export const respProto = {
  [Command.FETCH_USER]: 'user.FetchUserResp',
};

export const reqUrl = {
  [Command.FETCH_USER]: 'fetch_user',
};
