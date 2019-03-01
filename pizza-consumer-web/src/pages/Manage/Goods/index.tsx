import * as React from 'react';
import { Input, Button, Table, Divider } from 'antd';
import { ColumnProps } from 'antd/lib/table';
import { EntityState } from '@schemas/store';
import './index.less';
import i18n from '@src/utils/i18n';
// import { fetchGroup, fetchGoods } from '@src/net/goods';
import { GoodsEntity, GoodsGroupEntity } from '@src/store/reducers/entity/schema';
import { formater } from '@utils/time';
import { TIMEFOAMTER } from '@schemas/utils';

const Search = Input.Search;

const columns: ColumnProps<GoodsEntity>[] = [{
  key: 'goods_id',
  title: i18n('SDU_ID'),
  dataIndex: 'goods_id',
  width: 200,
  render: (goods_id) =>
    <div className="goods-item_itemID goods-item">{goods_id}</div>,
}, {
  key: 'goods_pic',
  title: i18n('商品图片'),
  dataIndex: 'goods_pic',
  width: 100,
  render: (goods_pic) => {
    const background = `url(${goods_pic}) center center no-repeat`;
    return (
      <div className="goods-item_itemPic goods-item">
        <div
          className="goods-item_pic"
          style={{
            background,
            backgroundSize: 'cover',
          }}
        />
      </div>
    );
  },

}, {
  key: 'goods_name',
  title: i18n('商品名称'),
  dataIndex: 'goods_name',
  width: 300,
  render: (goods_name) =>
    <div className="goods-item_itemName goods-item">{goods_name}</div>,
}, {
  key: 'goods_price',
  title: i18n('价格'),
  dataIndex: 'goods_price',
  width: 100,
  render: (goods_price) =>
    <div className="goods-item_itemPrice goods-item">{`¥${goods_price}`}</div>,
}, {
  key: 'operation',
  title: i18n('操作'),
  dataIndex: 'operation',
  render: () =>
    <div className="goods-item">
      <a href="javascript:;">{i18n('编辑')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('删除')}</a>
    </div>,
}];

export interface GoodsState {
  keyword: string;
  pageIdx: number;
}

export interface GoodsProps {
  entity: EntityState;
  updateGoods(goods: GoodsEntity[]): void;
  updateGoodsGroup(group: GoodsGroupEntity[]): void;
}

export default class Goods extends React.PureComponent<GoodsProps, GoodsState> {
  constructor(props: GoodsProps) {
    super(props);
    this.state = {
      keyword: '',
      pageIdx: 1,
    };
  }

  componentDidMount() {
    // this.init();
  }

  // async init() {
  //   const { keyword, pageIdx } = this.state;
  //   const { updateGoods, updateGoodsGroup } = this.props;
  //   try {
  //     const groupResult = await fetchGroup();
  //     const currGroup = groupResult.response[0];
  //     if (currGroup) {
  //       const group_id = currGroup.group_id;
  //       const goodsResult = await fetchGoods({
  //         group_id,
  //         pageIdx,
  //         keyword,
  //       });
  //       updateGoods(goodsResult.response);
  //     }
  //     updateGoodsGroup(groupResult.response);
  //   } catch (e) { }
  // }

  renderGoods() {
    const { entity } = this.props;
    const { goods: goodInfo } = entity;

    return <div className="goods-goodsTable">
      <Table
        columns={columns}
        dataSource={goodInfo.goods}
        rowKey={(record) => (record.goods_id)}
        pagination={false}
      />
    </div>;
  }

  renderGroup() {
    const { entity } = this.props;
    const { goods: goodInfo } = entity;
    const { group } = goodInfo;

    return (
      <div className="goods-groupList">
        {
          group.map(groupItem => (
            <div
              className="goods-groupItem"
              key={groupItem.group_id}
            >
              <div className="goods-groupItemName">{groupItem.group_name}</div>
              <div className="goods-groupItemTime">
                {formater(parseInt(groupItem.group_time, 10), TIMEFOAMTER.TYPE2)}
              </div>
              <div className="goods-groupItemNum">{`商品数：${groupItem.group_num}`}</div>
            </div>
          ))
        }
      </div>
    );
  }

  render() {
    return (
      <div className="goods-wrapper">
        <div className="goods-header">
          <div className="goods-headerLeft">
            <div className="goods-headerSearch">
              <Search enterButton placeholder={i18n('搜索项目商品名称 或 SKUID')} />
            </div>
          </div>
          <div className="goods-headerRight">
            <Button type={'primary'}>{i18n('新增商品')}</Button>
          </div>
        </div>
        <div className="goods-main">
          <div className="goods-mainContent">
            <div className="goods-group">
              <div className="goods-addGroup">
                {i18n('新增商品组')}
              </div>
              <div className="goods-groups">
                {this.renderGroup()}
              </div>
            </div>
            <div className="goods-table">
              {this.renderGoods()}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
