import { AddressMap } from '@entity/schema';

import { UPDATE_ADDRESS } from './action';

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
    default:
      return state;
  }
};
