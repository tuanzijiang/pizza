const koa = require('koa2');
const cors = require('koa2-cors');
const bodyParser = require('koa-bodyparser')

const app = new koa();
const PORT = 3001;

app.use(cors());

app.use(bodyParser());

app.use(async (ctx, next) => {
  const url = ctx.request.url;
  const method = ctx.request.method;
  const body = ctx.request.body;
  const query = ctx.request.query;
  const header = ctx.request.header;
  console.info(url, method, body, query, header['content-type']);
  next();
});

app.use(async ctx => {
  ctx.body = 'Hello World';
});

app.listen(PORT);
console.info(`mock server start as port: ${PORT}`);
