import { OrderMap } from '@entity/schema';

import { UPDATE_ORDER } from './action';

export default (state: OrderMap = {}, action: any) => {
  switch (action.type) {
    case UPDATE_ORDER: {
      const order = action.order;
      if (order.id) {
        return {
          ...state,
          [order.id]: order,
        };
      }
    }
    default:
      return state;
  }
};
