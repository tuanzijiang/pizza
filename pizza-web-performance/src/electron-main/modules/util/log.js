const { PREFIX_NAME } = require('../../config');

const log = (prefix, msg) => {
  console.log(`[${PREFIX_NAME[prefix]}]: `, msg);
}

module.exports = {
  log,
}
