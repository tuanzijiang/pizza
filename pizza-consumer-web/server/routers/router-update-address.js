const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const Address = require('../entity/Address');
const _ = require('lodash');

const root = Root.fromJSON(proto);
const reqProtoType = 'address.UpdateAddressReq';
const respProtoType = 'address.UpdateAddressResp';
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
      response = await net.post('/updateUserAddress', result);
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
  command: 'UPDATE_ADDRESS',
  reqUrl: '/update_address',
};
