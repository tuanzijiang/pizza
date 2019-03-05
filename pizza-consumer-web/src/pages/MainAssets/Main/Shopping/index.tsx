import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import Icon from '@biz-components/Icon';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import { CART_ORDER_ID } from '@entity/Order';
import { updateOrderApi } from '@services/api-update-order';

interface ShoppingCartProps {
  menu: Order;
  pizzas: Pizza[];
}

interface ShoppingCartState {

}

export default class Shopping extends React.PureComponent<ShoppingCartProps, ShoppingCartState> {

  @autobind
  handleMenuUpdateClick(pizzaId: number, count: number) {
    return () => {
      updateOrderApi({
        pizzaId,
        count,
        orderId: CART_ORDER_ID,
      });
    };
  }

  renderOrder() {
    const { menu, pizzas } = this.props;
    const { pizzas: pizzasId, num } = menu;

    const orderPizzaIds = pizzasId.filter(v => num[v] !== 0);

    return (
      <>
        {
          orderPizzaIds.map(orderPizzaId => {
            const orderPizza = pizzas[orderPizzaId];
            const orderPizzaNum = num[orderPizzaId];
            const orderPizzaPrice = orderPizza.price;
            const orderPizzaName = orderPizza.name;
            return (
              <div
                key={orderPizzaId}
                className="shoppingCart-pizzaItem"
              >
                <div className="shoppingCart-pizzaItemImg">
                  <Icon name="pisa" classnames="shoppingCart-pizzaItemPisa" />
                </div>
                <div className="shoppingCart-pizzaItemContent">
                  <div className="shoppingCart-pizzaItemName">
                    {orderPizzaName}
                  </div>
                  <div className="shoppingCart-pizzaItemPrice">
                    <span>{i18n('¥')}</span>
                    {orderPizzaPrice}
                  </div>
                </div>
                <div className="shoppingCart-pizzaItemCount">
                  <Icon
                    name="delete"
                    onClick={this.handleMenuUpdateClick(orderPizzaId, orderPizzaNum - 1)}
                  />
                  <span className="shoppingCart-pizzaItemCountFont">
                    {orderPizzaNum}
                  </span>
                  <Icon
                    name="add-fill"
                    onClick={this.handleMenuUpdateClick(orderPizzaId, orderPizzaNum + 1)}
                  />
                </div>
              </div>
            );
          })
        }
      </>
    );
  }

  render() {
    const { menu } = this.props;

    return (
      <div className="shoppingCart-wrapper">
        {menu && this.renderOrder()}
      </div>
    );
  }
}
