import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';

interface ShoppingCartProps {
  menu: Order;
  pizzas: Pizza[];
}

interface ShoppingCartState {
}

export default class Shopping extends React.PureComponent<ShoppingCartProps, ShoppingCartState> {

  render() {
    return (
      <div className="shoppingCart-wrapper">
        shopping
      </div>
    );
  }
}
