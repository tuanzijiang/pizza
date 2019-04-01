const { app } = require('electron')

// render模块
const { createWindow, send: renderSend, on: renderOn } = require('./modules/render');

// log模块
const { log } = require('./modules/util/log');

// webloader模块
const { start: webloaderStart, send: webloaderSend, on: webloaderOn } = require('./modules/webLoader');

// 配置
const { DEFAULT_LOADER_URL, PREFIX_NAME } = require('./config');

// 启动webloader模块
webloaderStart();

// 启动render模块
app.on('ready', createWindow.bind(this, DEFAULT_LOADER_URL));

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', createWindow.bind(this, DEFAULT_LOADER_URL));

// 处理render进程的事件
renderOn((jsonParam) => {
  log(PREFIX_NAME.RENDER_FROM_RENDER, jsonParam);

  webloaderSend(jsonParam);
});

// 处理webloader进程的事件
webloaderOn((jsonParam) => {
  log(PREFIX_NAME.WEBLOADER_FROM_WEBLOADER, jsonParam);

  renderSend(jsonParam);
})

// const ElectronEvent = require('./events');
// const { fork } = require('child_process');
// const path = require('path');

// let mainWindow;

// const createWindow = () => {
//   mainWindow = new BrowserWindow({
//     width: 1200,
//     height: 700,
//     webPreferences: {
//       nodeIntegration: true,
//     }
//   });

//   mainWindow.loadURL('http://localhost:8000/index.html')

//   mainWindow.on('close', () => {
//     mainWindow = null;
//   });

//   const forked = fork(path.resolve(__dirname, './webLoader/index.js'))

//   const electronEvent = new ElectronEvent(mainWindow, forked);

//   // forked接收来自webLoader/index.js中的事件
//   forked.on('message', (param) => {
//     const { command, args } = JSON.parse(param);
//     switch (command) {
//       case 'RECORD_STOP': {
//         electronEvent.send('RECORD_STOP');
//         break;
//       }
//       case 'INFO': {
//         electronEvent.send('INFO', args);
//         break;
//       }
//       case 'CommandExec': {
//         electronEvent.send('CommandExec', args);
//       }
//       case 'RequestExec': {
//         electronEvent.send('RequestExec', args);
//       }
//       default: {
//         break;
//       }
//     }
//   });

//   // forked发送事件在on里面，即command/...Exec.js中
//   electronEvent.on();
// }


