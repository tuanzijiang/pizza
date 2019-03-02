const { getRandomNum, getRandomStr } = require('../utils/random');

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
    }
  }

  static fromJS(user) {
    return new User(user);
  }

  static random() {
    return new User({
      id: getRandomNum(5),
      name: `name_${getRandomStr(2)}`,
      phone: `phone_${getRandomStr(2)}`,
      email: `email_${getRandomStr(2)}`,
      birthday: getRandomNum(7),
      city: `city_${getRandomStr(2)}`,
      img: `img_${getRandomStr(2)}`,
    });
  }
}

module.exports = User;
