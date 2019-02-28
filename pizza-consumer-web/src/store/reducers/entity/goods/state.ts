import { GoodsEntity, GoodsGroupEntity, GoodsInfoEntity } from './schema';

export class Goods implements GoodsEntity {
  public goods_id = '';
  public goods_pic = '';
  public goods_price = '';
  public goods_name = '';
  public goods_description = '';
}

export class Group implements GoodsGroupEntity {
  public group_id = '';
  public group_name = '';
  public group_time = '';
  public group_num = 0;
}

export class GoodsInfo implements GoodsInfoEntity  {
  public goods: Goods[] = [];
  public group: Group[] = [];
}
