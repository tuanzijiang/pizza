const log = require('./utils/log');
const koa = require('koa2');
const cors = require('koa2-cors');
const bodyParser = require('koa-bodyparser')
const Router = require('koa-router');
const activityRouter = require('./routers/activity');
const goodsRouter = require('./routers/goods');
const app = new koa();
const PORT = 3000;
const VERSION = 'v0.0.1';

app.use(cors());

app.use(bodyParser());

app.use(async (ctx, next) => {
  const url = ctx.request.url;
  const method = ctx.request.method;
  const body = ctx.request.body;
  const query = ctx.request.query;
  log.warn(url, method, body, query);
  next();
});

const router = new Router();

router.use(`/${VERSION}/goods`, goodsRouter.routes(), goodsRouter.allowedMethods());
router.use(`/${VERSION}/activity`, activityRouter.routes(), activityRouter.allowedMethods());

app.use(router.routes(), router.allowedMethods());

app.listen(3000);
log.info(`mock server start as port: ${PORT}`);
