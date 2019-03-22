import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';
import { OrderStatusSchema } from '@entity/Order/schema';

enum PAY_KIND {
  ALIPAY = 0,
  WECHATE = 1,
}

const pay_config = [{
  id: PAY_KIND.ALIPAY,
  title: <div>支付宝</div>,
}, {
  id: PAY_KIND.WECHATE,
  title: <div>微信支付</div>,
}];

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

const Order_Status_Button = {
  [OrderStatusSchema.CART]: '下单',
  [OrderStatusSchema.UNPAID]: '支付',
  [OrderStatusSchema.PAID]: '取消订单',
  [OrderStatusSchema.CANCEL_CHECKING]: '再来一单',
  [OrderStatusSchema.CANCELED]: '再来一单',
  [OrderStatusSchema.CANCEL_FAILED]: '再来一单',
  [OrderStatusSchema.DELIVERING]: '取消订单',
  [OrderStatusSchema.RECEIVED]: '取消订单',
  [OrderStatusSchema.FINISH]: '再来一单',
};

interface OrderDetailProps {
  menu: Order;
  pizzas: {
    [key: number]: Pizza;
  };
  handleToOrderList: () => void;
}

interface OrderDetailState {
  pay_kind: PAY_KIND;
}

export default class OrderDetail extends React.PureComponent<OrderDetailProps, OrderDetailState> {
  constructor(props: OrderDetailProps) {
    super(props);
    this.state = {
      pay_kind: PAY_KIND.ALIPAY,
    };
  }

  componentDidMount() {
  }

  renderPayKind() {
    const { pay_kind } = this.state;
    return <>
      {
        pay_config.map(v => {
          const id = v.id;
          const title = v.title;
          return <div className="orderDetail-kindItem" key={id}>
            <div className="orderDetail-kindName">
              {title}
            </div>
            <div className="orderDetail-kindStatus">
              {pay_kind === id && <Icon name="success" classnames="orderDetail-kindSuccess" />}
            </div>
          </div>;
        })
      }
    </>;
  }

  renderPizzas() {
    const { menu, pizzas } = this.props;
    const { pizzas: pizzaIds } = menu;

    return <>
      {
        pizzaIds && pizzaIds.map((pizzaId: number) => {
          const currPizza = pizzas[pizzaId];
          const currNums = menu.num;
          const currNum = currNums[pizzaId];

          if (currNum === 0) {
            return null;
          }

          return (
            <div className="orderDetail-pizzaItem" key={pizzaId}>
              <div className="orderDetail-pizzaItemImage">
                <Icon name="pisa" classnames="settlement-pizzaItemPisa" />
              </div>
              <div className="orderDetail-pizzaItemName">
                {currPizza.name}
              </div>
              <div className="orderDetail-pizzaItemInfo">
                <div className="orderDetail-pizzaItemNum">
                  <span>{i18n('x')}</span>
                  <span>{currNum}</span>
                </div>
                <div className="orderDetail-pizzaItemPrice">
                  <span>{i18n('¥')}</span>
                  <span>{currPizza.price}</span>
                </div>
              </div>
            </div>
          );
        })
      }
    </>;
  }

  render() {
    const { handleToOrderList, menu, pizzas } = this.props;
    let price = 0;
    if (menu && menu.num) {
      const { num } = menu;
      price = Object.entries(num).reduce((prev, [pizzaId, count]) => {
        const currPizza = pizzas[parseInt(pizzaId, 10)];
        const perPrice = currPizza.price;
        return prev + (count as number) * perPrice;
      }, 0);
    }
    return (
      <div className="orderDetail-wrapper">
        <div className="orderDetail-main">
          <div className="orderDetail-back" onClick={handleToOrderList}>{i18n('< 返回列表')}</div>
          <div className="orderDetail-info">
            <div className="orderDetail-item orderDetail-list">
              <div className="orderDetail-itemHeader">{i18n('商品详情')}</div>
              <div className="orderDetail-itemBody">
                {menu && this.renderPizzas()}
              </div>
              <div className="orderDetail-itemFooter">
                {i18n('小计 ¥')}
                <span>{price}</span>
              </div>
            </div>
            {
              menu && menu.status === OrderStatusSchema.UNPAID
              && <div className="orderDetail-item orderDetail-kinds">
                <div className="orderDetail-itemHeader">{i18n('付款方式')}</div>
                <div className="orderDetail-itemBody">
                  {this.renderPayKind()}
                </div>
                <div className="orderDetail-itemFooter">
                  <div className="orderDetail-button">
                    {i18n('支付')}
                  </div>
                </div>
              </div>
            }
            {
              menu && menu.status !== OrderStatusSchema.UNPAID
              && <div className="orderDetail-item orderDetail-kinds">
                <div className="orderDetail-itemHeader">{i18n('订单状态')}</div>
                <div className="orderDetail-itemBody">
                  <div className="orderDetail-itemText1">
                    {i18n(Order_Status_Text[menu.status])}
                  </div>
                  <div className="orderDetail-itemText2">
                    {i18n(Order_Status_Info[menu.status])}
                  </div>
                </div>
                <div className="orderDetail-itemFooter">
                  <div className="orderDetail-button">
                    {i18n(Order_Status_Button[menu.status])}
                  </div>
                </div>
              </div>
            }
          </div>
        </div>
      </div>
    );
  }
}
