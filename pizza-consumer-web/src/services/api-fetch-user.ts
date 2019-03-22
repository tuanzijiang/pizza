import net, { Command } from '@net/base';
import { User } from '@entity/User';
import { Address } from '@entity/Address';
import store from '@store/index';
import { entity } from '@store/action';
import { FetchUserResp, FetchUserReq } from '@src/net/fetch-user';
import { Sex } from '@src/net/common';
import { SexSchema } from '@src/entity/schema';

export const fetchUserApi = async (param: FetchUserReq) => {
  const resp = await net.request(Command.FETCH_USER, param);
  const { resultType, user } = resp as FetchUserResp;

  if (resultType) {
    const { address } = user;
    const { id } = address;
    store.dispatch(entity.user.updateUser({
      ...user,
      address: id,
    }));
    store.dispatch(entity.addresses.updateAddress(Address.fromJS({
      ...address,
      sex: address.sex === Sex.FEMALE ? SexSchema.FEMALE : SexSchema.MALE,
    })));
  }

  return resp;
};
