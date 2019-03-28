const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');
const Address = require('../entity/Address');
const _ = require('lodash');
const argv = require('yargs').argv;
const net = require('../net');

const isMock = argv.isMock === 'true';
const root = Root.fromJSON(proto);
const reqProtoType = 'address.FetchAddressReq';
const respProtoType = 'address.FetchAddressResp';
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
      addresses: _.range(15).map(v => Address.random()),
    };
  } else {
    try {
      response = await net.post('/fetchUserAddresses', result);
      body = {
        resultType: 1,
        addresses: response.addresses.map(v => Address.fromJS(v)),
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
  command: 'FETCH_ADDRESS',
  reqUrl: '/fetch_address',
};
