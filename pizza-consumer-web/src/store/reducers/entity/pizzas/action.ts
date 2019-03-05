import { PizzaWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_PIZZA = Symbol('UPDATE_PIZZA');
export const UPDATE_PIZZAS = Symbol('UPDATE_PIZZAS');

/**
 * action creator
 */
const updatePizza = (pizza: PizzaWeakSchema) => ({
  pizza,
  type: UPDATE_PIZZA,
});

const updatePizzas = (pizzas: PizzaWeakSchema[]) => ({
  pizzas,
  type: UPDATE_PIZZAS,
});

export default {
  updatePizza,
  updatePizzas,
};
