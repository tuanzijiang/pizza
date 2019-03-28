import net, { Command } from '@net/base';
import { FetchMenuReq, FetchMenuResp } from '@src/net/api-fetch-menu';
import { Order, CART_ORDER_ID } from '@entity/Order';
import { Pizza } from '@entity/Pizza';
import store from '@store/index';
import { entity } from '@store/action';
import commonActionCreator from '@store/reducers/common/action';

export const fetchMenuApi = async (param: FetchMenuReq) => {
  const menu = await net.request(Command.FETCH_MENU, param);
  const { resultType, pizzas, cart } = menu as FetchMenuResp;
  const cartPizzaCount: {
    [key: number]: number,
  } = cart.pizzas.reduce((prev: any, curr) => {
    const pizzaId = curr.id;
    prev[pizzaId] = curr.count;
    return prev;
  }, {});

  if (resultType) {
    const pizzasInfo = pizzas.reduce((prev: any, curr) => {
      const { id } = curr;
      prev[0] = prev[0].concat(curr.id);
      prev[1][curr.id] = cartPizzaCount[id] ? cartPizzaCount[id] : 0;
      prev[2] = prev[2].concat(Pizza.fromJS(curr));
      return prev;
    }, [[], {}, []]);

    const cartOrder = Order.fromJS({
      pizzas: pizzasInfo[0],
      id: CART_ORDER_ID,
      num: pizzasInfo[1],
    });

    store.dispatch(entity.pizzas.updatePizzas(pizzasInfo[2]));
    store.dispatch(entity.orders.updateOrder(cartOrder));
    store.dispatch(commonActionCreator.updateCartId(cart.id));
  }
  return menu;
};
