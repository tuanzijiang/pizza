const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const _ = require('lodash');
const net = require('../net');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';
const root = Root.fromJSON(proto);
const reqProtoType = 'order.PayOrderReq';
const respProtoType = 'order.PayOrderResp';
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
      form: '<div>123</div>',
    };
  } else {
    try {
      response = await net.post('/pay', result);
      body = {
        resultType: 1, 
        form: response.form,
      }
    } catch (e) {
      body = {
        resultType: 0,
        form: '',
      }
    }
  }

  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'PAY_ORDER',
  reqUrl: '/pay_order',
};
