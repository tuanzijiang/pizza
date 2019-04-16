import { PizzaWeakSchema } from '@entity/schema';

/**
 * action type
 */
export const UPDATE_PIZZA = Symbol('UPDATE_PIZZA');
export const UPDATE_PIZZAS = Symbol('UPDATE_PIZZAS');
export const CLEAR = Symbol('CLEAR');

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

const clear = () => ({
  type: CLEAR,
});

export default {
  updatePizza,
  updatePizzas,
  clear,
};
