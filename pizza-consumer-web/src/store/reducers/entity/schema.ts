import { ActivitiesEntity } from './activity/schema';
export {
  ActivitiesEntity, ActivityEntity, PageEntity,
  ActivityStateEntity,
} from './activity/schema';
import { GoodsInfoEntity } from './goods/schema';
export { GoodsEntity, GoodsGroupEntity, GoodsInfoEntity } from './goods/schema';
import { DisplayInfoEntity } from './display/schema';

export interface EntityState {
  activity: ActivitiesEntity;
  goods: GoodsInfoEntity;
  display: DisplayInfoEntity;
}
