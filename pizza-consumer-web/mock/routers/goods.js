const _ = require('lodash');
const Router = require('koa-router');
const { getRandomStr } = require('../utils/random');
const pics = require('../utils/pic');

const router = new Router();

router.get('/goods', async (ctx, next) => {
  ctx.body = _.range(10).map(() => ({
    goods_id: getRandomStr(15),
    goods_name: '二十个字的长度的题目一二三四五六七八九',
    goods_pic: pics[_.random(2)],
    goods_price: _.random(100),
    goods_description: '描述：二十个字的长度的题目一二三四五六七八九',
  }));
});

router.get('/group', async (ctx, next) => {
  ctx.body = _.range(10).map(() => ({
    group_id: getRandomStr(15),
    group_name: '二十个字的长度的题目一二三四五六七八九',
    group_time: new Date().valueOf().toString(),
    group_num: _.random(500),
  }));
});

module.exports = router;
