import { combineReducers } from 'redux';
import pagePc from './page-pc/reducer';
import pageMobile from './page-mobile/reducer';
import entity from './entity/reducer';
import common from './common/reducer';

export default combineReducers({
  entity,
  pageMobile,
  pagePc,
  common,
});
