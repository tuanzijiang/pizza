const Router = require('koa-router');
const { Root } = require('protobufjs');
const Order = require('../entity/Order');
const proto = require('../proto.json');
const _ = require('lodash');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true'
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

  // mock
  let body;

  if (isMock) {
    body = {
      resultType: 1,
    };
  } else {
    try {
      response = await net.post('/updateOrder', result);
      body = {
        resultType: 1,
      }
    } catch (e) {
      body = {
        resultType: 0,
      }
    }
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
