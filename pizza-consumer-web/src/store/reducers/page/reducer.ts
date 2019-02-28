import { combineReducers } from 'redux';
import manage from './manage/reducer';
import edit from './edit/reducer';

export default combineReducers({
  manage,
  edit,
});
