import { combineReducers } from 'redux';
import user from './user/reducer';
import addresses from './addresses/reducer';
import orders from './orders/reducer';
import pizzas from './pizzas/reducer';

export default combineReducers({
  user,
  addresses,
  orders,
  pizzas,
});
