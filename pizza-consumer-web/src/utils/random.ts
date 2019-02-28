import _ from 'lodash';

const CHARS = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'.split('');
const LENGTH = CHARS.length;

export const getRandomStr = (num: number) =>
  _.range(num).reduce((prev) =>
    `${prev}${CHARS[_.random(0, LENGTH - 1)]}`
    , '');
