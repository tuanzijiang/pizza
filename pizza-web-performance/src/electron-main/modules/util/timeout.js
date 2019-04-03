const maxTimePromise = (time, shouldResolve) => new Promise((resolve, reject) => {
  if (shouldResolve) {
    setTimeout(() => {
      resolve();
    }, time);
  } else {
    setTimeout(() => {
      reject('事件超时');
    }, time);
  }
})

module.exports = {
  maxTimePromise,
}
