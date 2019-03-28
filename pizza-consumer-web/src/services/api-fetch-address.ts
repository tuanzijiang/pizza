import net, { Command } from '@net/base';
import { entity } from '@store/action';
import { User } from '@entity/User';
import { FetchAddressResp, FetchAddressReq } from '@src/net/api-fetch-address';
import store from '@store/index';
import { Address } from '@net/common';
import { add, OBJECT_STORE_NAMES } from '@utils/db';

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

    // @ts-ignore
    await add(OBJECT_STORE_NAMES.ADDRESS, Object.values(addresses));
    await add(OBJECT_STORE_NAMES.USER, [__STORE__.getState().entity.user]);
  }

  return resp;
};
