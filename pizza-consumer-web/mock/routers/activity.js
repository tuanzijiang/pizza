const _ = require('lodash');
const Router = require('koa-router');
const { getRandomStr } = require('../utils/random');

const router = new Router();

router.get('/', async (ctx, next) => {
  ctx.body = _.range(10).map(() => ({
    activity_id: getRandomStr(15),
    activity_name: '二十个字的长度的题目一二三四五六七八九',
    creator_name: '章琪',
    time: {
      start: new Date().valueOf().toString(),
      end: new Date().valueOf().toString(),
    },
    state: _.random(3),
    pages: _.range(5).map(() => ({
      page_name: '二十个字的长度的题目一二三四五六七八九',
      page_id: getRandomStr(15),
      creator_name: '章琪',
      time: {
        start: new Date().valueOf().toString(),
        end: new Date().valueOf().toString(),
      },
      state: _.random(3),
    })),
  }));
});

module.exports = router;
