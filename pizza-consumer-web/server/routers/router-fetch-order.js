const Router = require('koa-router');
const { Root } = require('protobufjs');
const Order = require('../entity/Order');
const proto = require('../proto.json');
const net = require('../net');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';
const root = Root.fromJSON(proto);
const reqProtoType = 'order.FetchOrderReq';
const respProtoType = 'order.FetchOrderResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

const map = {
  UNKONWN: 0,
  CART: 1,
  UNPAID: 2,
  PAID: 3,
  CANCEL_CHECKING: 4,
  CANCELED: 5,
  CANCEL_FAILED: 6,
  DELIVERING: 7,
  RECEIVED: 8,
  FINISH: 9,
  WAIT_DELIVERY: 10,
  RECEIVE_FAIL: 11,
}

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);
  const { orderId } = result;

  // mock
  let body;

  if (isMock) {
    const order = Order.random();
    order.id = orderId;
    body = {
      order,
      resultType: 1,
    };
  } else {
    try {
      response = await net.post('/fetchOrder', result);
      const order = response.order;
      order.status = map[order.status];
      body = {
        resultType: 1,
        order: response.order,
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
  command: 'FETCH_ORDER',
  reqUrl: '/fetch_order',
};
