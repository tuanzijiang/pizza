import { PizzaMap } from '@entity/schema';

import { UPDATE_PIZZA } from './action';

export default (state: PizzaMap = {}, action: any) => {
  switch (action.type) {
    case UPDATE_PIZZA: {
      const pizza = action.pizza;
      if (pizza.id) {
        return {
          ...state,
          [pizza.id]: pizza,
        };
      }
    }
    default:
      return state;
  }
};
