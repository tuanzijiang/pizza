const info = (...params) => {
  console.info(params);
}

const warn = (...params) => {
  console.warn(params);
}

const error = (...params) => {
  console.error(params);
}

module.exports = {
  info,
  warn,
  error,
}
