import { UPDATE_GOODS, UPDATE_GOODS_GROUP } from './action';
import { GoodsInfoEntity } from './schema';
import { GoodsInfo } from './state';

export default (state: GoodsInfoEntity = new GoodsInfo(), action: any) => {
  switch (action.type) {
    case UPDATE_GOODS: {
      return {
        ...state,
        goods: action.goods,
      };
    }
    case UPDATE_GOODS_GROUP: {
      return {
        ...state,
        group: action.group,
      };
    }
    default:
      return state;
  }
};
