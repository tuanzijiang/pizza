import { AddressWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_ADDRESS = Symbol('UPDATE_ADDRESS');

/**
 * action creator
 */
const updateAddress = (address: AddressWeakSchema) => ({
  address,
  type: UPDATE_ADDRESS,
});

export default {
  updateAddress,
};
