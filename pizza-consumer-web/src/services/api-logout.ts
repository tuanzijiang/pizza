import { clear, OBJECT_STORE_NAMES } from '@utils/db';

export const logoutApi = async () => {
  clear(OBJECT_STORE_NAMES.USER);
  clear(OBJECT_STORE_NAMES.ADDRESS);
  clear(OBJECT_STORE_NAMES.ORDER);
  clear(OBJECT_STORE_NAMES.PIZZA);
};
