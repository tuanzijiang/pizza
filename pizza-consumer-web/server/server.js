const koa = require('koa2');
const cors = require('koa2-cors');
const bodyParser = require('koa-bodyparser')
const Router = require('koa-router');
const getRawBody = require('raw-body');

const routers = require('./routers/index');
const log = require('./utils/log');

const app = new koa();
const PORT = 3000;

app.use(cors());

app.use(bodyParser());

app.use(async (ctx, next) => {
  ctx.proto = await getRawBody(ctx.req, { length: ctx.request.length });
  const url = ctx.request.url;
  const method = ctx.request.method;
  const body = ctx.request.body;
  const query = ctx.request.query;
  const header = ctx.request.header;
  log.info(url, method, body, query, header['content-type']);
  await next();
});

const router = new Router();

routers.forEach(({reqUrl, reqRouter}) => {
  router.use(reqUrl, reqRouter.routes(), reqRouter.allowedMethods())
});

app.use(router.routes(), router.allowedMethods());

app.listen(3000);
log.info(`mock server start as port: ${PORT}`);
