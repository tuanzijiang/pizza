const { getRandomNum, getRandomStr } = require('../utils/random');

export class Order {

  constructor(order) {
    if (order) {
      this.id = order.id || '';
      this.startTime = order.startTime || 0;
      this.pizzas = order.pizzas || [];
      this.address = order.address || 0;
      this.status = order.status || 0;
      this.phone = order.phone || '';
      this.num = order.num || {};
    }
  }

  static fromJS() {
    return new Order(order);
  }

  static random() {
    return new Order({
      id: `id_${getRandomStr(2)}`,
      startTime: getRandomNum(5),
      pizzas: [],
      address: `addressId_${getRandomStr(2)}`,
      status: getRandomNum(1),
      phone: `phone_${getRandomStr(2)}`,
      num: {},
    });
  }
}
