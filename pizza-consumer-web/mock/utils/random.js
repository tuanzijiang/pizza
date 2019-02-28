const _ = require('lodash');

const CHARS = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'.split('');
const LENGTH = CHARS.length;

const getRandomStr = (num) => _.range(num).reduce((prev) => `${prev}${CHARS[_.random(0, LENGTH - 1)]}`, '');

module.exports = {
  getRandomStr
}
