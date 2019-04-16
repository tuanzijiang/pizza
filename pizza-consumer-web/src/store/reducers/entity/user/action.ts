import { UserWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_USER = Symbol('UPDATE_USER');

export const CLEAR = Symbol('CLEAR');

/**
 * action creator
 */
const updateUser = (user: UserWeakSchema) => ({
  user,
  type: UPDATE_USER,
});

const clear = () => ({
  type: CLEAR,
});

export default {
  updateUser,
  clear,
};
