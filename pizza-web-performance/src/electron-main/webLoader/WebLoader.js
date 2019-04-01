const puppeteer = require('puppeteer');

const recordEvent = require('./event');
const middleware = require('./middleware');

const { EVENT_BIND_FUNC } = recordEvent;
const {
  consoleMiddleware, registerMiddleware, closeMiddleware,
  netMiddleware,
} = middleware;

// const CHROME_PATH = '/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome';

// todo: pages, on, testing
class WebLoader {
  constructor(props) {
    const { url, events, headless, cb } = props;
    const { onStopRecord } = cb;
    this.url = url;
    this.events = events;
    this.headless = headless;
    this.onStopRecord = onStopRecord;
    this.startTime = new Date().valueOf();
    this.stopTime = 0;
    this.commands = [];
    this.response = {
      success: [],
      error: [],
    };
    this.status = '';
    this.testPages = [];
    this.testingNum = 0;
    this.testFlag = true;
  }

  async startRecord() {
    //初始化
    this.status = 'RECORD';
    this.commands = [];
    this.startTime = new Date().valueOf();
    this.stopTime = new Date().valueOf();
    this.headless = false;
    this.response = {
      success: [],
      error: [],
    }

    // 打开浏览器
    this.browser = await puppeteer.launch({
      headless: this.headless,
      // executablePath: CHROME_PATH,
    });

    // 打开页面
    this.page = await this.browser.newPage()
    await this.page.goto(this.url);

    // 应用中间件
    this.apply([consoleMiddleware, registerMiddleware, closeMiddleware, netMiddleware]);

    // 绑定事件
    this.events.forEach(event => {
      const bindFunc = EVENT_BIND_FUNC[event];
      if (bindFunc) {
        this.page.evaluate(bindFunc);
      }
    });
  }

  async playRecord() {
    this.status = 'PLAY';
    this.headless = false;

    // 打开浏览器
    this.browser = await puppeteer.launch({
      headless: this.headless,
      // executablePath: CHROME_PATH,
    });

    // 打开页面
    this.page = await this.browser.newPage()
    await this.page.goto(this.url);

    // 执行脚本
    this.exec(0, () => {
      this.browser.close();
    });
  }

  async exec(i, onExit, pageIdx, afterCommand) {
    const command = this.commands[i];
    const page = pageIdx !== undefined ? this.testPages[pageIdx] : this.page;

    page.evaluate((pageIdx) => {
      window.__PAGE_IDX = pageIdx;
    }, pageIdx);

    // command不存在的时候
    if (!command) {
      setTimeout(() => {
        page.close();
        if (onExit) {
          onExit();
        }
      }, this.stopTime);
      return;
    }

    // command存在的时候
    const { name, interval, selector, value } = command;
    setTimeout(async () => {
      try {
        switch (name) {
          case 'CLICK': {
            await page.click(selector);
            afterCommand({
              name,
              selector,
              pageIdx,
            });
            break;
          }
          case 'CHANGE': {
            await page.focus(selector)
            page.keyboard.type(value)
            afterCommand({
              name,
              selector,
              pageIdx,
              value,
            });
            break;
          }
          default: {
            break;
          }
        }
      } catch (e) {
        console.error('webloader exec', e);
      }

      this.exec(i + 1, onExit, pageIdx, afterCommand);
    }, interval);
  }

  async testRecord(afterCommand, afterRequest) {
    //初始化
    this.status = 'TEST';
    this.startTime = new Date().valueOf();
    // this.headless = true;

    // 打开浏览器
    this.browser = await puppeteer.launch({
      headless: this.headless,
      // executablePath: CHROME_PATH,
    });

    // 测试
    const idx = this.testPages.length;
    const page = await this.browser.newPage();
    this.testingNum += 1;
    page.on('close', () => {
      this.testingNum -= 1;

      // 判断是否要结束进程
      if (this.testingNum === 0 && this.testFlag === false) {
        this.browser.close();
      }
    });
    this.testPages.push(page);
    await page.goto(this.url);

    // 对请求失败的监听
    page.on('requestfailed', (request) => {
      afterRequest({
        state: 'error',
        url: request._url,
        method: request._method,
        pageIdx: idx,
      });
    })

    // 对请求成功的监听
    page.on('request', (request) => {
      afterRequest({
        state: 'success',
        url: request._url,
        method: request._method,
        pageIdx: idx,
      });
    })

    this.exec(0, () => { }, idx, afterCommand);

    setInterval(async () => {
      // 超过峰值以后不再加压
      if (this.testingNum >= 5) {
        return;
      }

      // 判断加压是否结束
      const currTime = new Date().valueOf();
      if (currTime - this.startTime > 10000) {
        this.testFlag = false;
        return;
      }

      const idx = this.testPages.length;
      const page = await this.browser.newPage();
      page.on('close', () => {
        this.testingNum -= 1;
      });
      this.testingNum += 1;

      // 对请求失败的监听
      page.on('requestfailed', (request) => {
        afterRequest({
          state: 'error',
          url: request._url,
          method: request._method,
          pageIdx: idx,
        });
      })

      // 对请求成功的监听
      page.on('request', (request) => {
        afterRequest({
          state: 'success',
          url: request._url,
          method: request._method,
          pageIdx: idx,
        });
      })

      this.testPages.push(page);
      await page.goto(this.url);

      this.exec(0, () => { }, idx, afterCommand);
    }, 2000);
  }

  apply(middlewares) {
    middlewares.forEach(middleware => {
      middleware(this);
    });
  }

  add(command) {
    this.commands = this.commands.concat(command);
  }

  stopRecord() {
    this.status = '';
    this.browser.close();
    this.browser = null;
    this.page = null;
    this.commands = this.commands.map(command => ({
      ...command,
      time: command.timestamp - this.startTime,
    }))

    this.commands.forEach((command, idx) => {
      const prevTime = (this.commands[idx - 1] || {}).time || 0;
      command.interval = command.time - prevTime;
    })

    const lastCommand = this.commands[this.commands.length - 1];
    if (lastCommand) {
      this.stopTime = new Date().valueOf() - lastCommand.timestamp;
    } else {
      this.stopTime = new Date().valueOf() - this.startTime;
    }

    this.onStopRecord();
  }

  isRecord() {
    return this.status === 'RECORD';
  }

  output() {

  }
}

module.exports = WebLoader;