const { fork } = require('child_process');
const { PREFIX_NAME } = require('../../config');
const { log } = require('../util/log');
const path = require('path');

let forked;
let handle

const on = (func) => {
  handle = func;
}

const send = (jsonParam) => {
  if(!forked) {
    log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('forked对象不存在，无法send'));
    return;
  }

  log(PREFIX_NAME.WEBLOADER_FROM_MAIN, jsonParam);
  forked.send(jsonParam);
}

const start = () => {
  if (forked) {
    log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('已经存在一个webloader进程'));
    return;
  }

  forked = fork(path.resolve(__dirname, './forked.js'))

  forked.on('message', (...args) => {
    if(handle) {
      handle(...args);
    } else {
      log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('对webloader模块的事件监听函数不存在'));
    }
  });
}


module.exports = {
  start,
  send,
  on,
}
