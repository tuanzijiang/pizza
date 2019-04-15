const Router = require('koa-router');
const { Root } = require('protobufjs');
const Order = require('../entity/Order');
const proto = require('../proto.json');
const _ = require('lodash');
const argv = require('yargs').argv;
const net = require('../net');

const isMock = argv.isMock === 'true'
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
  let body;

  if (isMock) {
    body = {
      resultType: 1,
      orders: _.range(5).map(v => Order.random()),
    };
  } else {
    try {
      response = await net.post('/fetchOrders', result);
      body = {
        resultType: 1,
        orders: response.orders.map(v => Order.fromJS(v)),
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
  command: 'FETCH_ORDERS',
  reqUrl: '/fetch_orders',
};
