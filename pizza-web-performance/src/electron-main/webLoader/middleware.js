const { getCommandClass } = require('./command');

const registerMiddleware = (webLoader) => {
  webLoader.page.evaluate(() => {
    const getDOMToken = (dom, needCount) => {
      const tagName = dom.tagName;
      const tagId = dom.id ? `#${dom.id}` : '';
      const tagClass = Array.prototype.map.apply(
        dom.classList, [v => `.${v}`]
      ).reduce((prev, curr) => (`${prev}${curr}`), '');
      const tokenWithoutCount = `${tagName}${tagId}${tagClass}`;

      if (needCount) {
        let i = 1;
        let brotherDom = dom.previousElementSibling;
        while (brotherDom) {
          i++;
          brotherDom = brotherDom.previousElementSibling;
        }
        return `${tokenWithoutCount}:nth-child(${i})`;
      }
      return tokenWithoutCount;
    };

    const fetchSelector = (dom) => {
      const domTokenPath = [];
      let currDom = dom;
      while (currDom !== document) {
        const token = getDOMToken(currDom, true);
        domTokenPath.unshift(token);
        currDom = currDom.parentNode;
      }
      return domTokenPath.join('>');
    }

    const COMMAND_PREFIX = '__COMMAND:';
    const COMMAND_NAME = {
      CLICK: 'CLICK',
      CHANGE: 'CHANGE',
    }

    const sendCommand = (name, param) => {
      console.warn(`${COMMAND_PREFIX}${COMMAND_NAME[name]}`, param);
    }

    window.__LOADER__ = window.__LOADER__ || {};
    window.__LOADER__.getDOMToken = getDOMToken;
    window.__LOADER__.fetchSelector = fetchSelector;
    window.__LOADER__.sendCommand = sendCommand;
    window.__LOADER__.COMMAND_PREFIX = COMMAND_PREFIX;
    window.__LOADER__.COMMAND_NAME = COMMAND_NAME;
  });
}

const consoleMiddleware = (webLoader) => {
  webLoader.page.on('console', (msg) => {
    if (!msg.args()[0]) {
      return;
    }
    const handle = msg.args()[0].toString();

    // 当前为command的指令
    if (/^JSHandle:__COMMAND:/.test(handle)) {
      const param = msg.args()[1].toString();
      const CommandClass = getCommandClass(handle);
      if (!CommandClass) {
        console.error(`[err]: 未知的Command:${handle}`);
        return;
      }
      const command = CommandClass.generate(param);
      webLoader.add(command);
    }
  });
}

const netMiddleware = (webLoader) => {
  if (webLoader.isRecord()) {
    webLoader.page.on('requestfailed', (request) => {
      const { response } = webLoader;
      const { error } = response;
      response.error = error.concat({
        url: request._url,
        method: request._method,
      })
    })

    webLoader.page.on('request', (request) => {
      const { response } = webLoader;
      const { success } = response;
      response.success = success.concat({
        url: request._url,
        method: request._method,
      })
    })
  }
}

const closeMiddleware = (webLoader) => {
  if (webLoader.isRecord()) {
    webLoader.page.on('close', () => {
      webLoader.stopRecord();
    })
  }
}



module.exports = {
  consoleMiddleware,
  registerMiddleware,
  closeMiddleware,
  netMiddleware,
}
