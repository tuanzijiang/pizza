import { User } from '@entity/index';

import { UPDATE_USER } from './action';

export default (state: User = User.fromJS(), action: any) => {
  switch (action.type) {
    case UPDATE_USER: {
      return state.update(action.user);
    }
    default:
      return state;
  }
};
