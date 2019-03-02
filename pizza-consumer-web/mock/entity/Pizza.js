const { getRandomNum, getRandomStr } = require('../utils/random');

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
    }
  }

  static fromJS(pizza) {
    return new Pizza(pizza);
  }

  static random() {
    return new Pizza({
      id: getRandomNum(5),
      name: `name_${getRandomStr(2)}`,
      description: `desc_${getRandomStr(2)}`,
      price: getRandomNum(2),
      tag: `tag_${getRandomStr(2)}`,
      img: `img_${getRandomStr(2)}`,
      state: getRandomNum(1),
    });
  }
}

module.exports = Pizza;
