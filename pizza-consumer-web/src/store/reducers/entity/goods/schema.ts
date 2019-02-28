export interface GoodsEntity {
  goods_id: string;
  goods_pic: string;
  goods_price: string;
  goods_name: string;
  goods_description: string;
}

export interface GoodsGroupEntity {
  group_id: string;
  group_name: string;
  group_time: string;
  group_num: number;
}

export interface GoodsInfoEntity {
  goods: GoodsEntity[];
  group: GoodsGroupEntity[];
}
