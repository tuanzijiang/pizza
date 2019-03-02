import { combineReducers } from 'redux';
import page from './page/reducer';
import entity from './entity/reducer';

export default combineReducers({
  page,
  entity,
});
