import net, { Command } from '@net/base';
import { LoginReq, LoginResp } from '@src/net/api-login';
import store from '@store/index';
import { entity } from '@store/action';
import { Address } from '@entity/Address';
import { Sex } from '@src/net/common';
import { SexSchema } from '@src/entity/schema';

export const loginApi = async (param: LoginReq) => {
  const resp = await net.request(Command.LOGIN, param);
  const { resultType, user } = resp as LoginResp;

  if (resultType) {
    const { address } = user;
    if (address) {
      const { id } = address;
      store.dispatch(entity.user.updateUser({
        ...user,
        address: id,
      }));
      store.dispatch(entity.addresses.updateAddress(Address.fromJS({
        ...address,
        sex: address.sex === Sex.FEMALE ? SexSchema.FEMALE : SexSchema.MALE,
      })));
    } else {
      store.dispatch(entity.user.updateUser({
        ...user,
        address: 0,
      }));
    }

    return true;
  }

  return false;
};
