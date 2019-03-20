const Router = require('koa-router');
const { Root } = require('protobufjs');
const log = require('../utils/log');
const proto = require('../proto.json');
const Pizza = require('../entity/Pizza');
const _ = require('lodash');

const root = Root.fromJSON(proto);
const reqProtoType = 'order.FetchMenuReq';
const respProtoType = 'order.FetchMenuResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);
  log.info('[fetch_menu_req]:', result);

  // mock
  const { userId } = result;
  const body = {
    resultType: 1,
    pizzas: _.range(15).map(v => Pizza.random()),
  }

  log.info('[fetch_menu_resp]:', body);
  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'FETCH_MENU',
  reqUrl: '/fetch_menu',
};
