import net, { Command } from '@net/base';
import { entity } from '@store/action';
import { UpdateAddressReq, UpdateAddressResp } from '@src/net/api-update-address';
import store from '@store/index';
import { Address } from '@net/common';

export const updateAddressApi = async (param: UpdateAddressReq) => {
  const resp = await net.request(Command.UPDATE_ADDRESS, param);
  const { resultType } = resp as UpdateAddressResp;

};
