import net, { Command } from '@net/base';
import { User } from '@entity/User';
import { Address } from '@entity/Address';
import store from '@store/index';
import { entity } from '@store/action';
import { FetchUserResp, FetchUserReq } from '@src/net/api-fetch-user';
import { Sex } from '@src/net/common';
import { SexSchema } from '@src/entity/schema';
import { add, OBJECT_STORE_NAMES } from '@utils/db';

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

    // 插入数据库
    await add(OBJECT_STORE_NAMES.USER, [user as unknown as User]);
    await add(OBJECT_STORE_NAMES.ADDRESS, [address as unknown as Address]);
  }

  return resp;
};
