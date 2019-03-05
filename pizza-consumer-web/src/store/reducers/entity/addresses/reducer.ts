import { AddressMap } from '@entity/schema';

import { UPDATE_ADDRESS, UPDATE_ADDRESSES } from './action';

export default (state: AddressMap = {}, action: any) => {
  switch (action.type) {
    case UPDATE_ADDRESS: {
      const address = action.address;
      if (address.id) {
        return {
          ...state,
          [address.id]: address,
        };
      }
    }
    case UPDATE_ADDRESSES: {
      const addresses = action.addresses;
      return {
        ...state,
        ...addresses,
      };
    }
    default:
      return state;
  }
};
