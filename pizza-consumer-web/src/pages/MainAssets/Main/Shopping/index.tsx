import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import Icon from '@biz-components/Icon';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import { CART_ORDER_ID } from '@entity/Order';
import { updateOrderApi } from '@services/api-update-order';
import { MainAssetName } from '../..';
import { OpenType } from '@biz-components/PageAssets';

interface ShoppingCartProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
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

  @autobind
  handleSettlementClick() {
    const { onPageChange } = this.props;
    onPageChange(MainAssetName.Settlement, OpenType.RIGHT);
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
    const { menu, pizzas } = this.props;
    let price = 0;
    if (menu) {
      const { num } = menu;
      price = Object.entries(num).reduce((prev, [pizzaId, count]) => {
        const currPizza = pizzas[parseInt(pizzaId, 10)];
        const perPrice = currPizza.price;
        return prev + perPrice * count;
      }, 0);
    }

    return (
      <div className="shoppingCart-wrapper">
        <div className="shoppingCart-content">
          {menu && this.renderOrder()}
        </div>
        <div className="shoppingCart-sum">
          <span className="shoppingCart-prompt">
            {i18n('应付合计:')}
          </span>
          <span className="shoppingCart-unit">
            {i18n('¥')}
          </span>
          <span className="shoppingCart-totalPrice">
            {price}
          </span>
          <span
            className="shoppingCart-order"
            onClick={this.handleSettlementClick}
          >
            {i18n('去结算')}
          </span>
        </div>
      </div>
    );
  }
}
