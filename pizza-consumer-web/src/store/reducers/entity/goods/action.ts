import { GoodsEntity, GoodsGroupEntity } from './schema';

/**
 * action type
 */
export const UPDATE_GOODS = Symbol('UPDATE_GOODS');
export const UPDATE_GOODS_GROUP = Symbol('UPDATE_GOODS_GROUP');

/**
 * action creator
 */
const updateGoods = (goods: GoodsEntity[]) => ({
  goods,
  type: UPDATE_GOODS,
});

const updateGoodGroup = (group: GoodsGroupEntity[]) => ({
  group,
  type: UPDATE_GOODS_GROUP,
});

export default {
  updateGoods,
  updateGoodGroup,
};
