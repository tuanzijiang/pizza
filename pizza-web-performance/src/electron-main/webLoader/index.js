const recordEvent = require('./event');
const WebLoader = require('./WebLoader');
const process = require('process');

const { EVENT_NAME } = recordEvent;

const onStopRecord = () => {
  const param = {
    command: 'RECORD_STOP',
    args: [],
  };

  process.send(JSON.stringify(param));
};

const onInfoMsg = (...msg) => {
  const param = {
    command: 'INFO',
    args: msg,
  }

  process.send(JSON.stringify(param));
}

const onCommandExec = (...args) => {
  const param = {
    args,
    command: 'CommandExec',
  }

  process.send(JSON.stringify(param));
}

const onRequestExec = (...args) => {
  const param = {
    args,
    command: 'RequestExec',
  }

  process.send(JSON.stringify(param));
}

const webloaders = [];

// 监听main进程发的指令，发送的函数定一个在command/...Exec.js中
process.on('message', (param) => {
  const { command, args } = JSON.parse(param);
  switch (command) {
    case 'RECORD': {
      let needWebloader = true;
      webloaders.forEach(webloader => {
        if (webloader.status === 'RECORD') {
          needWebloader = false;
        }
      })
      if (!needWebloader) {
        onInfoMsg('已存在一个正在录制的脚本');
        return;
      }

      const options = {
        url: 'http://localhost:8080/index.html',
        events: [EVENT_NAME.CLICK, EVENT_NAME.CHANGE], // 需要监听的事件
        headless: false,
        cb: {
          onStopRecord,
        }
      }

      const webLoader = new WebLoader(options);
      webloaders.push(webLoader);
      webLoader.startRecord();
      break
    }
    case 'PLAY': {
      let needRecord = webloaders.length === 0;
      if (needRecord) {
        onInfoMsg('没有可播放的脚本');
        return;
      }

      const webLoader = webloaders[webloaders.length - 1];

      webLoader.playRecord();
      break;
    }
    case 'TEST': {
      let needRecord = webloaders.length === 0;
      if (needRecord) {
        onInfoMsg('没有可执行的脚本');
        return;
      }

      const webLoader = webloaders[webloaders.length - 1];

      webLoader.testRecord(onCommandExec, onRequestExec);
      break;
    }
    default: {
      break;
    }
  }
});
