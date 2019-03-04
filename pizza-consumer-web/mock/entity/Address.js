const { getRandomNum, getRandomStr } = require('../utils/random');

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
      name: `name_${getRandomStr(2)}`,
      sex: getRandomNum(1),
      address: `address_${getRandomStr(2)}`,
      addressDetail: `addressDetail_${getRandomStr(2)}`,
      phone: `phone_${getRandomStr(2)}`,
      tag: `tag_${getRandomStr(2)}`,
    });
  }
}

module.exports = Address;
