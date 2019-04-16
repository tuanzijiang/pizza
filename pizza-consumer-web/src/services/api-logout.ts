import { clear, OBJECT_STORE_NAMES } from '@utils/db';
import store from '@store/index';
import { entity } from '@store/action';

export const logoutApi = async () => {
  clear(OBJECT_STORE_NAMES.USER);
  clear(OBJECT_STORE_NAMES.ADDRESS);
  clear(OBJECT_STORE_NAMES.ORDER);
  clear(OBJECT_STORE_NAMES.PIZZA);

  store.dispatch(entity.user.clear());
  store.dispatch(entity.addresses.clear());
  store.dispatch(entity.orders.clear());
  store.dispatch(entity.pizzas.clear());
};
