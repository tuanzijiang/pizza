const resolve = (text) => {
  const str = `${text}`;
  const strArr = str.split(':');
  if (strArr.length !== 2) {
    return false;
  }
  return {
    ip: strArr[0],
    port: strArr[1],
  }
};

module.exports = resolve;
