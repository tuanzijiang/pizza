import * as React from 'react';
import './index.scss';
import { Pizza, Address, Order } from '@src/entity';
import i18n from '@src/utils/i18n';
import { MainAssetName } from '../../index';
import { OpenType } from '@biz-components/PageAssets';
import { formater, TIMEFOAMTER } from '@utils/time';
import autobind from 'autobind-decorator';

interface OrderListProps {
  pizzas: {
    [key: number]: Pizza;
  };
  addresses: {
    [key: number]: Address;
  };
  orders: {
    [key: string]: Order;
  };
  orderIds: string[];
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
}

interface OrderListState { }

export default class OrderList extends React.PureComponent<OrderListProps, OrderListState> {

  @autobind
  handleDetailClick(orderId: string) {
    return () => {
      const { onPageChange } = this.props;
      onPageChange(MainAssetName.OrderDetail, OpenType.RIGHT, orderId);
    };
  }

  render() {
    const { orderIds, orders, pizzas } = this.props;
    return (
      <div className="orderList-wrapper">
        {
          orderIds.map((orderId: string) => {
            const order = orders[orderId];
            const pizzaIds = order.pizzas;
            const firstPizza = pizzas[pizzaIds[0]];
            const firstPizzaName = firstPizza.name;
            const pizzaNum = pizzaIds.length;

            const num = order.num;
            const price = Object.entries(num).reduce((prev, [id, count]) => {
              const currPizza = pizzas[parseInt(id, 10)];
              const perPrice = currPizza.price;
              return prev + perPrice * count;
            }, 0);

            const startTime = formater(order.startTime, TIMEFOAMTER.TYPE3);

            return (
              <div
                key={orderId}
                className="orderList-item"
              >
                <div className="orderList-itemName">
                  <span>{firstPizzaName}{i18n('等')}</span>
                  <span>{i18n('共')}{pizzaNum}{i18n('件商品')}</span>
                </div>
                <div className="orderList-itemPrice">
                  <span>{i18n('¥')}</span>
                  {price}
                </div>
                <div className="orderList-itemOther">
                  {startTime}
                  <span
                    onClick={this.handleDetailClick(orderId)}
                  >
                    {i18n('查看详情')}
                  </span>
                </div>
              </div>
            );
          })
        }
      </div>
    );
  }
}
