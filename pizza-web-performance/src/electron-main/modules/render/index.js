const { BrowserWindow, ipcMain } = require('electron');
const { DEFAULT_HEIGHT, DEFAULT_WIDTH } = require('./config');
const { log } = require('../util/log');
const { PREFIX_NAME } = require('../../config');

let mainWindow;
let handle;

const send = (jsonParam) => {
  if (!mainWindow) {
    log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('windows对象不存在，无法send'));
    return;
  }

  mainWindow.webContents.send(PREFIX_NAME.RENDER_FROM_MAIN, jsonParam);
  log(PREFIX_NAME.RENDER_FROM_MAIN, jsonParam);
}

const on = (func) => {
  handle = func;
}

const createWindow = (url) => {
  if (mainWindow) {
    log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('windows对象已存在，不允许重新创建'));
    return;
  }

  mainWindow = new BrowserWindow({
    width: DEFAULT_WIDTH,
    height: DEFAULT_HEIGHT,
    webPreferences: {
      nodeIntegration: true,
    }
  })

  mainWindow.loadURL(url);

  mainWindow.on('close', () => {
    mainWindow = null;
  });

  ipcMain.on(PREFIX_NAME.RENDER_FROM_RENDER, (ev, ...args) => {
    if (handle) {
      handle(...args);
    } else {
      log(PREFIX_NAME.SYSTEM_WARN, JSON.stringify('对render模块的事件监听函数不存在'));
    }
  });
}

module.exports = {
  createWindow,
  send,
  on,
}

