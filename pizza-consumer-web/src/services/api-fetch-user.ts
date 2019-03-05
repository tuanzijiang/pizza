import net, { Command } from '@net/base';
import { User } from '@entity/User';
import store from '@store/index';
import { entity } from '@store/action';
import { FetchUserResp, FetchUserReq } from '@src/net/fetch-user';

export const fetchUserApi = async (param: FetchUserReq) => {
  const resp = await net.request(Command.FETCH_USER, param);
  const { resultType, user } = resp as FetchUserResp;

  if (resultType) {
    store.dispatch(entity.user.updateUser(User.fromJS(user)));
  }

  return resp;
};
