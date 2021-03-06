const Router = require('koa-router');
const { Root } = require('protobufjs');
const User = require('../entity/User');
const proto = require('../proto.json');
const argv = require('yargs').argv;
const net = require('../net');

const isMock = argv.isMock === 'true'
const root = Root.fromJSON(proto);
const reqProtoType = 'user.FetchUserReq';
const respProtoType = 'user.FetchUserResp';
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
      user: User.random(),
    };
  } else {
    try {
      response = await net.post('/fetchUser', result);
      body = {
        resultType: 1,
        user: response.user,
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
  command: 'FETCH_USER',
  reqUrl: '/fetch_user',
};
