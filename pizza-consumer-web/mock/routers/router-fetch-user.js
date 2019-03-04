const Router = require('koa-router');
const { Root } = require('protobufjs');
const User = require('../entity/User');
const proto = require('../proto.json');

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
  const body = {
    resultType: 0,
    user: User.random(),
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
