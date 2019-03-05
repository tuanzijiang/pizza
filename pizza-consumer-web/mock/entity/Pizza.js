const { getRandomNum, getRandomStr } = require('../utils/random');
const _ = require('lodash');

const PIZZA_NAME_ARR = [
  '北京潮鸭比萨S[纯珍]',
  '美式精选比萨S[纯珍]',
  '新奥尔良烤肉比萨S[纯珍]',
  '海鲜至尊比萨S[纯珍]',
  '超级至尊比萨S[纯珍]',
  '超级至尊比萨S[纯珍]',
  '黄金夏威夷比萨S[纯珍]',
  '韩式烤肉比萨[纯珍][9英寸]',
  '黄金夏威夷风光比萨[薄底][10英寸]',
];

const PIZZA_DESC_ARR = [
  '特选鲜嫩喷香鸭肉，搭配京味酱，配以黄瓜，京葱等传',
  '腊肉肠、莫扎里拉芝士。,主要原料:面团、芝士、腊肉肠',
];

const PIZZA_TAG_ARR = [
  '手拍纯珍比萨',
  '皇冠芝心比萨',
  '大方薄底比萨',
]

class Pizza {
  constructor(pizza) {
    if (pizza) {
      this.id = pizza.id || 0;
      this.name = pizza.name || '';
      this.description = pizza.description || '';
      this.price = pizza.price || 0;
      this.tag = pizza.tag || '';
      this.img = pizza.img || '';
      this.state = pizza.state || 0;
      this.count = pizza.count || 0;
    }
  }

  static fromJS(pizza) {
    return new Pizza(pizza);
  }

  static random() {
    return new Pizza({
      id: getRandomNum(5),
      name: PIZZA_NAME_ARR[_.random(0, PIZZA_NAME_ARR.length - 1)],
      description: PIZZA_DESC_ARR[_.random(0, PIZZA_DESC_ARR.length - 1)],
      price: getRandomNum(2),
      tag: PIZZA_TAG_ARR[_.random(0, PIZZA_TAG_ARR.length - 1)],
      img: `img_${getRandomStr(2)}`,
      state: _.random(0, 1),
      count: _.random(0, 10),
    });
  }
}

module.exports = Pizza;
