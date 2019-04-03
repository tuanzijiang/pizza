const puppeteer = require('puppeteer');
const { PREFIX_NAME } = require('../../config');
const { log } = require('../util/log');
const { getRandomStr } = require('../util/random')
const { maxTimePromise } = require('../util/timeout');
const { COMMAND_NAME } = require('./command/config');
const {
  initMiddleware,
  commandMiddleware,
  netMiddleware,
  closeMiddleware,
  pageEventMiddleware,
  EVENT_NAME
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

const exec = async (page, command) => {
  if (!command) {
    return null;
  }

  const { name, selector, value, time } = command;
  return new Promise((resolve, reject) => {
    setTimeout(async () => {
      try {
        switch (name) {
          case COMMAND_NAME.CLICK: {
            await page.click(selector);
            break;
          }
          case COMMAND_NAME.CHANGE: {
            await page.focus(selector);
            page.keyboard.type(value);
          }
        }
        resolve(command);
      } catch (e) {
        const args = {
          name,
          selector,
          value,
        }
        log(PREFIX_NAME.SYSTEM_ERROR, JSON.stringify(args));
        reject(e);
      }
    }, time);
  });
}

const handlePlay = async (record) => {
  const { url, commands } = record;

  // 启动浏览器
  const browser = await puppeteer.launch({
    headless: false,
    executablePath: CHROME_PATH,
  });

  // 打开页面
  const page = await browser.newPage();
  await page.goto(url);

  // 执行play
  const playExec = async (i) => {
    const command = commands[i];
    if (!command) {
      return null;
    }

    try {
      await exec(page, command);
      playExec(i + 1);
    } catch (e) {
      log(PREFIX_NAME.SYSTEM_ERROR, e);
    }
  }
  await playExec(0);

}

const handleTest = async (args, func) => {
  const {
    testAddInterval,
    testDuration,
    testNumRange,
    testTimeout,
    record,
  } = args;
  const { url, commands } = record;
  const startTime = new Date().valueOf();

  // 启动浏览器
  // 以有头模式进行，会因为page不在顶部而block住command
  const browser = await puppeteer.launch({
    headless: true,
    executablePath: CHROME_PATH,
  });

  const pages = [];
  const pageStopFlags = []; // 是否停止的flag
  let totalPageNum = 0;
  let timer;

  const run = async () => {
    // 应当结束运行
    const shouldEnd = new Date().valueOf() - startTime >= testDuration * 1000;
    if (shouldEnd && totalPageNum === 0) {
      clearInterval(timer);
      timer = null;
    }

    // 页面达到峰值不应该再加压
    if (totalPageNum >= testNumRange) {
      return;
    }

    // 添加页面
    const page = await browser.newPage();
    const pageIdx = pages.length;
    totalPageNum += 1;
    pages.push(page);

    try {
      await page.goto(url);

      // 网络请求监听
      netMiddleware(page, (response) => {
        const { isSuccess } = response;
        if (!isSuccess) {
          pageStopFlags[pageIdx] = true;
          func({ pageIdx, status: false, msg: '网络请求故障', detail: response })
        }
      })
    } catch (e) {
      // 关闭页面
      page.close();
      totalPageNum -= 1;

      log(PREFIX_NAME.SYSTEM_ERROR);

      // 页面打开失败
      if (func) {
        pageStopFlags[pageIdx] = true;
        func({ pageIdx, status: false, msg: '页面打开失败', detail: e })
      }
    }

    // 当前页面执行脚本
    const playExec = async (i, pageIdx) => {
      if (pageStopFlags[pageIdx]) {
        return;
      }

      const command = commands[i];
      if (!command) {
        page.close();
        totalPageNum -= 1;
        return;
      }

      try {
        await exec(page, command);
        // command执行成功
        if (func && !pageStopFlags[pageIdx]) {
          func({ pageIdx, status: true, msg: '指令执行成功', detail: command })
        }

        playExec(i + 1, pageIdx);
      } catch (e) {
        // 关闭页面
        page.close();
        totalPageNum -= 1;

        log(PREFIX_NAME.SYSTEM_ERROR);

        // command执行失败
        if (func) {
          pageStopFlags[pageIdx] = true;
          func({ pageIdx, status: false, msg: '指令执行失败', detail: command })
        }
      }
    }
    playExec(0, pageIdx)
  }

  run();
  timer = setInterval(run, testAddInterval * 1000);
}

module.exports = {
  handleRecord,
  handlePlay,
  handleTest,
}
