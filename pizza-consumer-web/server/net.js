const http = require('http');
const querystring = require('querystring');
const argv = require('yargs').argv;

const check = require('./utils/check');
const result = check(argv.address);
if (!result) {
  console.error('address error');
  return;
}
const {
  ip: hostname,
  port,
} = check(argv.address);
const prefix = '/pizza-consumer';

const post = (path = '/', data = {}) => new Promise((resolve, reject) => {
  const postData = JSON.stringify(data);
  const totalPath = `${prefix}${path}`;

  const options = {
    hostname,
    port,
    path: totalPath,
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    }
  }

  const req = http.request(options, (res) => {
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
      try {
        // resultType枚举映射
        const obj = JSON.parse(chunk);
        obj.resultType = obj.resultType === 'SUCCESS' ? 1 : 0;
        console.log(`[post]${totalPath} end: ${JSON.stringify(obj)}`);
        resolve(obj);
      } catch (e) {
        console.error(e);
        reject(e);
      }

    });
  });

  req.on('error', (err) => {
    console.error(err);
    reject(err);
  });

  console.log(`[post]${hostname}:${port}${totalPath} start: ${postData}`);
  req.write(postData);
  req.end();
})

module.exports = {
  post,
};
