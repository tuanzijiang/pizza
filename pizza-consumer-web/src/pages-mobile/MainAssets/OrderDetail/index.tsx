import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { MainAssetName } from '../index';
import autobind from 'autobind-decorator';
import { OrderStatusSchema } from '@entity/Order/schema';
import { neetStatusBar } from '@utils/device';
import { Order } from '@entity/Order';
import { Address } from '@entity/Address';
import cx from 'classnames';
import Icon from '@biz-components/Icon';

interface OrderDetailProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
  entityStore: any;
}

interface OrderDetailState {
  currOrderId: string;
}

const Order_Status_Text = {
  [OrderStatusSchema.CART]: '已添加',
  [OrderStatusSchema.UNPAID]: '未支付',
  [OrderStatusSchema.PAID]: '已支付',
  [OrderStatusSchema.CANCEL_CHECKING]: '取消中',
  [OrderStatusSchema.CANCELED]: '已取消',
  [OrderStatusSchema.CANCEL_FAILED]: '取消失败',
  [OrderStatusSchema.DELIVERING]: '配送中',
  [OrderStatusSchema.RECEIVED]: '已接单',
  [OrderStatusSchema.FINISH]: '已完成',
};

const Order_Status_Info = {
  [OrderStatusSchema.CART]: '您的订单已添加，请尽快提交',
  [OrderStatusSchema.UNPAID]: '您的订单已提交，请尽快完成支付',
  [OrderStatusSchema.PAID]: '您的订单尚未接单，请耐心等待',
  [OrderStatusSchema.CANCEL_CHECKING]: '您的订单正在取消，请耐心等待',
  [OrderStatusSchema.CANCELED]: '您的订单已取消',
  [OrderStatusSchema.CANCEL_FAILED]: '您的订单取消失败',
  [OrderStatusSchema.DELIVERING]: '您的订单正在配送，请耐心等待',
  [OrderStatusSchema.RECEIVED]: '您的订单已接单，骑手正在赶往商家',
  [OrderStatusSchema.FINISH]: '您的订单已完成',
};

export default class OrderDetail extends React.PureComponent<OrderDetailProps, OrderDetailState> {
  constructor(props: OrderDetailProps) {
    super(props);
    this.state = {
      currOrderId: '',
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(MainAssetName.Main, OpenType.LEFT);
  }

  renderMiddle() {
    return <span className="orderDetail-middle">{i18n('订单详情')}</span>;
  }

  componentDidEnter(...extraInfo: any[]) {
    this.setState({
      currOrderId: extraInfo[0],
    });
  }

  renderOrder() {
    const { currOrderId } = this.state;
    const { entityStore } = this.props;
    const { pizzas, addresses, orders, user } = entityStore;
    const currOrder = orders[currOrderId];

    const canBeCancel = Order.canBeCancel(currOrder);
    const canAgain = Order.canAgain(currOrder);
    const needPay = Order.needPay(currOrder);
    const pizzaIds = currOrder.pizzas;

    const currAddress = addresses[currOrder.address];

    return (
      <>
        <div className="orderDetail-status">
          <div className="orderDetail-statusName">
            {Order_Status_Text[(currOrder.status as unknown) as OrderStatusSchema]}
          </div>
          <div className="orderDetail-statusInfo">
            {Order_Status_Info[(currOrder.status as unknown) as OrderStatusSchema]}
          </div>
          <div className="orderDetail-statusButtons">
            {
              canBeCancel && <div className="orderDetail-statusButton">{i18n('取消订单')}</div>
            }
            {
              canAgain && <div className="orderDetail-statusButton">{i18n('再来一单')}</div>
            }
            {
              needPay && <div className="orderDetail-statusButton">{i18n('去支付')}</div>
            }
          </div>
        </div>
        <div className="orderDetail-pizzas">
          <div className="orderDetail-pizzasTitle">
            {i18n('商品信息')}
          </div>
          {
            pizzaIds.map((pizzaId: number) => {
              const currPizza = pizzas[pizzaId];
              const currNums = currOrder.num;
              const currNum = currNums[pizzaId];

              if (currNum === 0) {
                return null;
              }

              return (
                <div className="orderDetail-pizzaItem" key={pizzaId}>
                  <div className="orderDetail-pizzaItemImage">
                    <Icon name="pisa" classnames="orderDetail-pizzaItemPisa" />
                  </div>
                  <div className="orderDetail-pizzaItemName">
                    {currPizza.name}
                  </div>
                  <div className="orderDetail-pizzaItemNum">
                    <span>{i18n('x')}</span>
                    {currNum}
                  </div>
                  <div className="orderDetail-pizzaItemPrice">
                    <span>{i18n('¥')}</span>
                    {currPizza.price}
                  </div>
                </div>
              );
            })
          }
        </div>
        {
          currAddress && <div className="orderDetail-address">
            <div className="orderDetail-addressTitle">
              {i18n('配送信息')}
            </div>
            <div className="orderDetail-addressInfoItem">
              <div className="orderDetail-addressInfoItemTitle">
                {i18n('收货地址')}
              </div>
              <div className="orderDetail-addressInfoItemContent">
                {currAddress.address}
              </div>
            </div>
            <div className="orderDetail-addressInfoItem">
              <div className="orderDetail-addressInfoItemTitle">
                {i18n('联系电话')}
              </div>
              <div className="orderDetail-addressInfoItemContent">
                {currAddress.phone}
              </div>
            </div>
            <div className="orderDetail-addressInfoItem">
              <div className="orderDetail-addressInfoItemTitle">
                {i18n('联系人')}
              </div>
              <div className="orderDetail-addressInfoItemContent">
                {Address.toAppellation(currAddress)}
              </div>
            </div>
          </div>
        }
      </>
    );
  }

  render() {
    const { currOrderId } = this.state;

    return (
      <div className="orderDetail-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className={cx({
          'orderDetail-content': true,
          'orderDetail-content_status': neetStatusBar,
        })}>
          {currOrderId && this.renderOrder()}
        </div>
      </div>
    );
  }
}
