import { combineReducers } from 'redux';
import activity from './activity/reducer';
import goods from './goods/reducer';
import display from './display/reducer';

export default combineReducers({
  activity,
  goods,
  display,
});
