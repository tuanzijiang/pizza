import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import Icon from '@biz-components/Icon';
import i18n from '@src/utils/i18n';

interface ShoppingCartProps {
  menu: Order;
  pizzas: Pizza[];
}

interface ShoppingCartState {

}

export default class Shopping extends React.PureComponent<ShoppingCartProps, ShoppingCartState> {

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
                  <Icon name="delete" />
                  <span className="shoppingCart-pizzaItemCountFont">
                    {orderPizzaNum}
                  </span>
                  <Icon name="add-fill" />
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
