import net, { Command } from '@net/base';
import { entity } from '@store/action';
import { User } from '@entity/User';
import { FetchAddressResp, FetchAddressReq } from '@src/net/fetch-address';
import store from '@store/index';
import { Address } from '@net/common';

export const fetchAddressApi = async (param: FetchAddressReq) => {
  const resp = await net.request(Command.FETCH_ADDRESS, param);
  const { resultType, addresses } = resp as FetchAddressResp;

  if (resultType) {
    store.dispatch(entity.user.updateUser({
      addresses: addresses.map(v => v.id),
    }));
    store.dispatch(entity.addresses.updateAddresses(addresses.reduce((prev: {
      [key: number]: Address,
    }, curr) => {
      prev[curr.id] = curr;
      return prev;
    }, {})));
  }

  return resp;
};
