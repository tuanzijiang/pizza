import net, { Command } from '@net/base';
import { RegisterReq, RegisterResp } from '@src/net/api-register';
import store from '@store/index';
import { entity } from '@store/action';
import { Address } from '@entity/Address';
import { Sex } from '@src/net/common';
import { SexSchema } from '@src/entity/schema';

export const registerApi = async (param: RegisterReq) => {
  const resp = await net.request(Command.REGISTER, param);
  const { resultType, user } = resp as RegisterResp;

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
