const http = require('http');
const querystring = require('querystring');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';
const hostname = isMock ? 'localhost' : '172.30.225.106';
const port = isMock ? 3001 : 8080;
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
      // resultType枚举映射
      const obj = JSON.parse(chunk);
      obj.resultType = obj.resultType === 'SUCCESS' ? 1 : 0;
      console.log(`[post]${totalPath} end: ${JSON.stringify(obj)}`);
      resolve(obj);
    });
  });

  req.on('error', (err) => {
    console.error(err);
    reject(err);
  });

  console.log(`[post]${totalPath} start: ${postData}`);
  req.write(postData);
  req.end();
})

module.exports = {
  post,
};
