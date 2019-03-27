const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const Address = require('../entity/Address');
const _ = require('lodash');

const root = Root.fromJSON(proto);
const reqProtoType = 'address.AddAddressReq';
const respProtoType = 'address.AddAddressResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  // mock
  const body = {
    resultType: 1,
  }

  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'ADD_ADDRESS',
  reqUrl: '/add_address',
};
