const log = require('./utils/log');
const koa = require('koa2');
const cors = require('koa2-cors');
const bodyParser = require('koa-bodyparser')
const Router = require('koa-router');
const activityRouter = require('./routers/activity');
const goodsRouter = require('./routers/goods');
const getRawBody = require('raw-body');
const app = new koa();
const PORT = 3000;
const VERSION = 'v0.0.1';
const proto = require('./proto.json');
const { Root } = require('protobufjs');


const root = Root.fromJSON(proto);

app.use(cors());

app.use(bodyParser());

app.use(async (ctx, next) => {
  ctx.proto = await getRawBody(ctx.req, { length: ctx.request.length });
  const url = ctx.request.url;
  const method = ctx.request.method;
  const body = ctx.request.body;
  const query = ctx.request.query;
  const header = ctx.request.header;
  log.warn(url, method, body, query, header['content-type']);
  next();
});

const router = new Router();

router.post('/user', async (ctx, next) => {
  const protoBuff = ctx.proto;
  const reqType = root.lookupType('user.FetchUserReq');
  const result = reqType.decode(protoBuff);
  console.warn(1111, result);
  ctx.body = 123;
  next();
});

router.use(`/${VERSION}/goods`, goodsRouter.routes(), goodsRouter.allowedMethods());
router.use(`/${VERSION}/activity`, activityRouter.routes(), activityRouter.allowedMethods());


app.use(router.routes(), router.allowedMethods());

app.listen(3000);
log.info(`mock server start as port: ${PORT}`);
