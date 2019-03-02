
const { Command, reqUrls, reqRouters } = require('./config');

module.exports = Object.values(Command).map(command => ({
  reqUrl: reqUrls[command],
  reqRouter: reqRouters[command],
}));