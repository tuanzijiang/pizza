import { AddressWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_ADDRESS = Symbol('UPDATE_ADDRESS');
export const UPDATE_ADDRESSES = Symbol('UPDATE_ADDRESSES');
export const CLEAR = Symbol('CLEAR');

/**
 * action creator
 */
const updateAddress = (address: AddressWeakSchema) => ({
  address,
  type: UPDATE_ADDRESS,
});

const updateAddresses = (addresses: {
  [key: number]: AddressWeakSchema,
}) => ({
  addresses,
  type: UPDATE_ADDRESSES,
});

const clear = () => ({
  type: CLEAR,
});

export default {
  updateAddress,
  updateAddresses,
  clear,
};
