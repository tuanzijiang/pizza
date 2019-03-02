import { UserWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_USER = Symbol('UPDATE_USER');

/**
 * action creator
 */
const updateUser = (user: UserWeakSchema) => ({
  user,
  type: UPDATE_USER,
});

export default {
  updateUser,
};
