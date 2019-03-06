const info = (...params) => {
  console.info('\033[47;32m [info]',params);
}

const warn = (...params) => {
  console.info('\033[47;33m [info]',params);
}

const error = (...params) => {
  console.info('\033[47;31m [info]',params);
}

module.exports = {
  info,
  warn,
  error,
}
