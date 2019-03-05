import { combineReducers } from 'redux';
import login from './login/reducer';
import main from './main/reducer';

export default combineReducers({
  login,
  main,
});
