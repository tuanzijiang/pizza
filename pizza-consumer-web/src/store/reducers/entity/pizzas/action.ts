import { PizzaWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_PIZZA = Symbol('UPDATE_PIZZA');

/**
 * action creator
 */
const updatePizza = (pizza: PizzaWeakSchema) => ({
  pizza,
  type: UPDATE_PIZZA,
});

export default {
  updatePizza,
};
