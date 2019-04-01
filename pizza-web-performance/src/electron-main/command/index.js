const { recordExec } = require('./recordExec');
const { playExec } = require('./playExec');
const { testExec } = require('./testExec');
const { RECEIVE_COMMAND_NAME } = require('./config');

const execFuncMap = {
  [RECEIVE_COMMAND_NAME.RECORD]: recordExec,
  [RECEIVE_COMMAND_NAME.PLAY]: playExec,
  [RECEIVE_COMMAND_NAME.TEST]: testExec,
}

const getExecFunc = (name) => {
  return execFuncMap[name];
}

module.exports = {
  getExecFunc,
}
