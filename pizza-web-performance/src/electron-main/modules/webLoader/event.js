const puppeteer = require('puppeteer');
const { PREFIX_NAME } = require('../../config');
const { log } = require('../util/log');
const { getRandomStr } = require('../util/random')
const {
  initMiddleware,
  commandMiddleware,
  netMiddleware,
  closeMiddleware,
  pageEventMiddleware,
  EVENT_NAME,
} = require('./middlewares');

const CHROME_PATH = '/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome';

// 处理函数
const handleRecord = async (args, func) => {
  try {
    const commands = [];
    const errorResp = [];
    const successResp = [];
    const startTime = new Date().valueOf();

    // 解析参数
    const { url } = args;

    // 启动浏览器
    const browser = await puppeteer.launch({
      headless: false,
      executablePath: CHROME_PATH,
    });

    // 打开页面
    const page = await browser.newPage();
    await page.goto(url);

    // 对页面环境进行配置
    initMiddleware(page);

    // 对command输出
    commandMiddleware(page, (command) => {
      const lastCommandTime = commands.length === 0 ?
        startTime : commands[commands.length - 1].timestamp;

      command.time = command.timestamp - lastCommandTime;
      commands.push(command);
    });

    // 页面事件监听函数
    pageEventMiddleware(page, [EVENT_NAME.CLICK, EVENT_NAME.CHANGE]);

    netMiddleware(page, (response) => {
      const { isSuccess } = response;
      if (isSuccess) {
        successResp.push(response);
      } else {
        errorResp.push(response);
      }
    })

    closeMiddleware(page, async () => {
      const record = {
        url,
        commands,
        name: '',
        successResp,
        errorResp,
        id: getRandomStr(8),
        report: null,
      }

      browser.close();

      if (func) {
        func(record);
      }
    })
  } catch (e) {
    log(PREFIX_NAME.SYSTEM_ERROR, e);
  }
}

module.exports = {
  handleRecord,
}
