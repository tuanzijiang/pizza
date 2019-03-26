import net, { Command } from '@net/base';
import { FetchMenuReq, FetchMenuResp } from '@src/net/api-fetch-menu';
import { Order, CART_ORDER_ID } from '@entity/Order';
import { Pizza } from '@entity/Pizza';
import store from '@store/index';
import { entity } from '@store/action';

export const fetchMenuApi = async (param: FetchMenuReq) => {
  const menu = await net.request(Command.FETCH_MENU, param);
  const { resultType, pizzas } = menu as FetchMenuResp;

  if (resultType) {
    const pizzasInfo = pizzas.reduce((prev: any, curr) => {
      prev[0] = prev[0].concat(curr.id);
      prev[1][curr.id] = curr.count;
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
  }
  return menu;
};
