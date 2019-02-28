import { combineReducers } from 'redux';
import page from './page/reducer';
import userInfo from './userInfo/reducer';
import entity from './entity/reducer';

export default combineReducers({
  page,
  userInfo,
  entity,
});
