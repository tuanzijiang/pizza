import { UPDATE_ACTIVITIES } from './action';
import { ActivityEntity as ActivitySchema } from './schema';

export default (state: ActivitySchema[] = [], action: any) => {
  switch (action.type) {
    case UPDATE_ACTIVITIES: {
      return {
        ...state,
        activities: action.activities,
      };
    }
    default:
      return state;
  }
};
