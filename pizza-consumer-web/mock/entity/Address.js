const { getRandomNum, getRandomStr } = require('../utils/random');
const _ = require('lodash');

class Address {
  constructor(address) {
    if (address) {
      this.id = address.id || 0;
      this.name = address.name || '';
      this.sex = address.sex || 0;
      this.address = address.address || '';
      this.addressDetail = address.addressDetail || '';
      this.phone = address.phone || '';
      this.tag = address.tag || '';
    }
  }

  static fromJS(address) {
    return new Address(address);
  }

  static random() {
    return new Address({
      id: getRandomNum(5),
      name: '章琪',
      sex: _.random(0, 1),
      address: '上海市 普陀区 中山北路 3663号',
      addressDetail: '华东师范大学第五宿舍',
      phone: '15317315332',
      tag: '学校',
    });
  }
}

module.exports = Address;
