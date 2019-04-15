const { getRandomStr } = require('../utils/random');
const Address = require('./Address');
const Pizza = require('./Pizza');
const _ = require('lodash');

class Order {

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

  static fromJS(order) {
    return new Order(order);
  }

  static random() {
    return new Order({
      id: `id_${getRandomStr(2)}`,
      startTime: new Date().valueOf(),
      pizzas: _.range(0, 5).map(v => Pizza.random()),
      address: Address.random(),
      status: _.random(0, 8),
      phone: 15317315332,
      num: {},
    });
  }
}

module.exports = Order;
