const fs = require('fs');
const path = require('path');

const filenames = fs.readdirSync(path.resolve(__dirname, './')).filter(v => /^router-/.test(v));
const Command = {};
const reqUrls = {};
const reqRouters = {};

filenames.forEach(filename => {
  const pathname = path.resolve(__dirname, filename);
  const { router, command, reqUrl} = require(pathname);
  Command[command] = command;
  reqUrls[command] = reqUrl;
  reqRouters[command] = router;
});

module.exports = {
  Command,
  reqUrls,
  reqRouters,
}