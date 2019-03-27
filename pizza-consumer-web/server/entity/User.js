const { getRandomNum, getRandomStr } = require('../utils/random');
const Address = require('./Address');
const _ = require('lodash');

const USER_NAME_ARR = [
  '年少不负好时光',
];

const USER_TEL_ARR = [
  '15317315332'
];

const USER_CITY_ARR = [
  '上海',
  '北京',
]

class User {
  constructor(user) {
    if (user) {
      this.id = user.id || 0;
      this.name = user.name || '';
      this.phone = user.phone || '';
      this.email = user.email || '';
      this.birthday = user.birthday || 0;
      this.city = user.city || '';
      this.img = user.img || '';
      this.address = user.address || {};
    }
  }

  static fromJS(user) {
    return new User(user);
  }

  static random() {
    return new User({
      id: getRandomNum(5),
      name: USER_NAME_ARR[0],
      phone: USER_TEL_ARR[0],
      email: `zhangqi.67@bytedance.com`,
      birthday: new Date().valueOf(),
      city: USER_TEL_ARR[_.random(0, 1)],
      img: `img_${getRandomStr(2)}`,
      address: Address.random(),
    });
  }
}

module.exports = User;
