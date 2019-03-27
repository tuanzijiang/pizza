const Router = require('koa-router');
const { Root } = require('protobufjs');
const Order = require('../entity/Order');
const proto = require('../proto.json');
const _ = require('lodash');

const root = Root.fromJSON(proto);
const reqProtoType = 'order.FetchOrdersReq';
const respProtoType = 'order.FetchOrdersResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  // mock
  const body = {
    resultType: 1,
    orders: _.range(5).map(v => Order.random()),
  }

  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'FETCH_ORDERS',
  reqUrl: '/fetch_orders',
};
