const Router = require('koa-router');
const { Root } = require('protobufjs');
const User = require('../entity/User');
const proto = require('../proto.json');

const root = Root.fromJSON(proto);
const reqProtoType = 'user.LoginReq';
const respProtoType = 'user.LoginResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  // mock
  const body = {
    resultType: 1,
    user: User.random(),
  }

  const decodeBody = respType.encode(respType.create(body)).finish();

  ctx.body = decodeBody;
  next();
});

module.exports = {
  router,
  command: 'LOGIN',
  reqUrl: '/fetch_loginStatus',
};
