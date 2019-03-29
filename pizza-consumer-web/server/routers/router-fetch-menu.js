const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const Pizza = require('../entity/Pizza');
const Order = require('../entity/Order');
const _ = require('lodash');
const net = require('../net');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';

const root = Root.fromJSON(proto);
const reqProtoType = 'order.FetchMenuReq';
const respProtoType = 'order.FetchMenuResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  // mock
  const { userId } = result;

  let body;

  if (isMock) {
    const pizzas = _.range(15).map(v => Pizza.random());
    const cart = Order.random();
    cart.pizzas = pizzas;
    body = {
      resultType: 1,
      pizzas,
      cart, 
    };
  } else {
    try {
      response = await net.post('/fetchMenu', result);
      body = {
        resultType: response.resultType, 
        pizzas: response.pizzas.map(v => Pizza.fromJS(v)),
        cart: response.cart,
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
  command: 'FETCH_MENU',
  reqUrl: '/fetch_menu',
};
