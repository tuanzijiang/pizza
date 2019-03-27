const Router = require('koa-router');
const { Root } = require('protobufjs');
const Order = require('../entity/Order');
const proto = require('../proto.json');
const _ = require('lodash');

const root = Root.fromJSON(proto);
const reqProtoType = 'order.UpdateOrderReq';
const respProtoType = 'order.UpdateOrderResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  const { orderId, pizzaIs, count } = result;

  // // mock
  const body = {
    resultType: 1,
  }

  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'UPDATE_ORDER',
  reqUrl: '/update_order',
};
