const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const net = require('../net');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';
const root = Root.fromJSON(proto);
const reqProtoType = 'order.FetchPhoneReq';
const respProtoType = 'order.FetchPhoneResp';
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
    };
  } else {
    try {
      response = await net.post('/fetchPhone', result);
      body = {
        resultType: 1,
        deliverymanPhone: response.deliverymanPhone,
        shopPhone: response.shopPhone,
        servicePhone: response.servicePhone,
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
  command: 'FETCH_PHONE',
  reqUrl: '/fetch_phone',
};
