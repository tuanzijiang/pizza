const http = require('http');
const querystring = require('querystring');
const argv = require('yargs').argv;

const isMock = argv.isMock === 'true';
const hostname = isMock ? 'localhost' : '';
const port = isMock ? 3001 : 8080;

const post = (path = '/', data = {}) => new Promise((resolve, reject) => {
  const postData = querystring.stringify(data);

  const options = {
    hostname,
    port,
    path,
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;',
      'Content-Length': Buffer.byteLength(postData)
    }
  }

  const req = http.request(options, (res) => {
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
      console.log('BODY:' + chunk);
      resolve(chunk);
    });
  });

  req.on('error', (err) => {
    console.error(err);
    reject(err);
  });

  console.log(`${hostname}:${port}  ${path}`, postData);
  req.write(postData);
  req.end();
})

module.exports = {
  post,
};
