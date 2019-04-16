import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import i18n from '@src/utils/i18n';
import { formater, TIMEFOAMTER } from '@utils/time';
import autobind from 'autobind-decorator';
import { fetchOrderApi } from '@src/services/api-fetch-order';

interface OrderListProps {
  orderIds: string[];
  pizzas: {
    [key: number]: Pizza;
  };
  orders: {
    [key: string]: Order;
  };
  handleToOrderDetail: () => void;
  handleSetCurrentId: (currId: string) => void;
  userId: number;
}

interface OrderListState {
}

export default class OrderList extends React.PureComponent<OrderListProps, OrderListState> {
  constructor(props: OrderListProps) {
    super(props);
    this.state = {
    };
  }

  componentDidMount() {
  }

  @autobind
  handleDetailClick(id: string) {
    return () => {
      const { handleToOrderDetail, handleSetCurrentId, userId } = this.props;
      fetchOrderApi({
        userId,
        orderId: id,
      });
      handleSetCurrentId(id);
      handleToOrderDetail();
    };
  }

  render() {
    const { orders, orderIds, pizzas } = this.props;
    return (
      <div className="orderList-wrapper">
        <div className="orderList-main">
          {
            orderIds.map((orderId: string) => {
              const order = orders[orderId];
              if (!order) {
                return <></>;
              }
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
                    >
                    </span>
                  </div>
                  <div
                    className="orderList-itemScan"
                    onClick={this.handleDetailClick(orderId)}
                  >
                    {i18n('查看详情')}
                  </div>
                </div>
              );
            })
          }
        </div>
      </div>
    );
  }
}
