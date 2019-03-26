const Router = require('koa-router');
const { Root } = require('protobufjs');
const proto = require('../proto.json');

const root = Root.fromJSON(proto);
const reqProtoType = 'user.SendVerificationReq';
const respProtoType = 'user.SendVerificationResp';
const reqType = root.lookupType(reqProtoType);
const respType = root.lookupType(respProtoType);

const router = new Router();

router.post('/', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const result = reqType.decode(protoBuff);

  console.warn(1111, result);
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
  command: 'SEND_VERIFICATION',
  reqUrl: '/send_verification',
};
