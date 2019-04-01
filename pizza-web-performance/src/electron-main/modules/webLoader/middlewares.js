const { getCommandClass } = require('./command')

// 页面全家变量的中间件
const COMMAND_PREFIX = '__COMMAND:';
const COMMAND_NAME = {
  CLICK: 'CLICK',
  CHANGE: 'CHANGE',
}

const initMiddleware = (page) => {
  page.evaluate((COMMAND_PREFIX, COMMAND_NAME) => {
    window.__LOADER__ = window.__LOADER__ || {};

    // 获取当前DOM唯一token的函数
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
    window.__LOADER__.getDOMToken = getDOMToken;

    // 获取当前DOM唯一selector的函数
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
    window.__LOADER__.fetchSelector = fetchSelector;

    // 上报command到webloader
    window.__LOADER__.COMMAND_PREFIX = COMMAND_PREFIX;
    window.__LOADER__.COMMAND_NAME = COMMAND_NAME;
    const sendCommand = (name, param) => {
      console.warn(`${COMMAND_PREFIX}${COMMAND_NAME[name]}`, param);
    }
    window.__LOADER__.sendCommand = sendCommand;

  }, COMMAND_PREFIX, COMMAND_NAME);
}

// 处理command的中间件，command以console的方式传递
const commandMiddleware = (page, func) => {
  page.on('console', (msg) => {
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
      func(command);
    }
  });
}

// 处理网络请求的中间件
const netMiddleware = (page, func) => {
  page.on('requestfailed', (request) => {
    func({
      url: request._url,
      method: request._method,
      isSuccess: false,
    })
  })

  page.on('request', (request) => {
    func({
      url: request._url,
      method: request._method,
      isSuccess: true,
    })
  })
}

// 处理page关闭事件的中间件
const closeMiddleware = (page, func) => {
  page.on('close', () => {
    func();
  })
}

// 处理page中的事件的中间件
const EVENT_NAME = {
  CLICK: 'CLICK',
  CHANGE: 'CHANGE',
}
const EVENT_BIND_FUNC = {
  [EVENT_NAME.CLICK]: () => {
    document.body.addEventListener('click', (ev) => {
      const selector = __LOADER__.fetchSelector(ev.target);
      window.__LOADER__.sendCommand(window.__LOADER__.COMMAND_NAME.CLICK, JSON.stringify({
        selector,
      }));
    });
  },
  [EVENT_NAME.CHANGE]: () => {
    document.body.addEventListener('change', (ev) => {
      const selector = __LOADER__.fetchSelector(ev.target);
      const value = ev.target.value;
      window.__LOADER__.sendCommand(window.__LOADER__.COMMAND_NAME.CHANGE, JSON.stringify({
        selector,
        value,
      }));
    })
  }
}
const pageEventMiddleware = (page, events) => {
  events.forEach(event => {
    const bindFunc = EVENT_BIND_FUNC[event];
    if (bindFunc) {
      page.evaluate(bindFunc);
    }
  });
}

module.exports = {
  closeMiddleware,
  netMiddleware,
  commandMiddleware,
  initMiddleware,
  pageEventMiddleware,
  EVENT_NAME,
}
