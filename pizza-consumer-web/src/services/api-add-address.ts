import net, { Command } from '@net/base';
import { AddAddressReq, AddAddressResp } from '@src/net/api-add-address';

export const addAddressApi = async (param: AddAddressReq) => {
  const resp = await net.request(Command.ADD_ADDRESS, param);
  const { resultType } = resp as AddAddressResp;
};
