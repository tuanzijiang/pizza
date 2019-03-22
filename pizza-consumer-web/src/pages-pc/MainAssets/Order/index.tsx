import * as React from 'react';
import './index.scss';
import { fetchOrdersApi } from '@src/services/api-fetch-orders';
import { fetchAddressApi } from '@src/services/api-fetch-address';
import { Order as OrderEntity, Address, Pizza } from '@src/entity';
import { OrderStatus } from '@net/common';
import OrderList from './OrderList';
import OrderDetail from './OrderDetail';
import autobind from 'autobind-decorator';

interface OrderProps {
  orderIds: string[];
  addresses: {
    [key: number]: Address;
  };
  pizzas: {
    [key: number]: Pizza;
  };
  orders: {
    [key: string]: OrderEntity;
  };
}

interface OrderState {
  navIdx: number;
  currOrderId: string;
}

export default class Order extends React.PureComponent<OrderProps, OrderState> {
  constructor(props: OrderProps) {
    super(props);
    this.state = {
      navIdx: 0,
      currOrderId: '',
    };
  }

  componentDidMount() {
    fetchOrdersApi({
      userId: 123,
      lastOrderId: '',
      num: 10,
      status: OrderStatus.PAID,
    });
    fetchAddressApi({
      userId: 123,
    });
  }

  @autobind
  handleNavChange(navIdx: number) {
    return () => {
      this.setState({
        navIdx,
      });
    };
  }

  @autobind
  handleSetCurrentId(currOrderId: string) {
    this.setState({
      currOrderId,
    });
  }

  render() {
    const { orders, pizzas, orderIds, addresses } = this.props;
    const { navIdx, currOrderId } = this.state;
    return (
      <div className="order-wrapper">
        {navIdx === 0 && <OrderList
          orders={orders}
          orderIds={orderIds}
          pizzas={pizzas}
          handleToOrderDetail={this.handleNavChange(1)}
          handleSetCurrentId={this.handleSetCurrentId}
        />
        }
        {navIdx === 1 && <OrderDetail
          menu={orders[currOrderId]} pizzas={pizzas}
          addresses={addresses}
          handleToOrderList={this.handleNavChange(0)} />
        }
      </div>
    );
  }
}
