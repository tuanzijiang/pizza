import { PizzaMap, PizzaWeakSchema } from '@entity/schema';

import {
  UPDATE_PIZZA,
  UPDATE_PIZZAS,
} from './action';

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
    case UPDATE_PIZZAS: {
      const pizzas = action.pizzas;
      const updatePizzas = pizzas.reduce((prev: any, curr: PizzaWeakSchema) => {
        const pizzaId = curr.id;
        prev[pizzaId] = curr;
        return prev;
      }, {});
      return {
        ...state,
        ...updatePizzas,
      };
    }
    default:
      return state;
  }
};
