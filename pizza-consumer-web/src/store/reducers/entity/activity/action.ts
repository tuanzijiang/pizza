import { ActivityEntity } from './schema';

/**
 * action type
 */
export const UPDATE_ACTIVITIES = Symbol('UPDATE_ACTIVITIES');

/**
 * action creator
 */
const updateActivities = (activities: ActivityEntity[]) => ({
  activities,
  type: UPDATE_ACTIVITIES,
});

export default {
  updateActivities,
};
