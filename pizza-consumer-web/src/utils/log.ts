const info = (...params: any) => {
  console.info(...params);
};

const warn = (...params: any) => {
  console.warn(...params);
};

const error = (...params: any) => {
  console.error(...params);
};

export default {
  info,
  warn,
  error,
};
