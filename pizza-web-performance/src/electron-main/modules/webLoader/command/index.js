const { COMMAND_NAME } = require('./config');
const ClickCommand = require('./ClickCommand');
const ChangeCommand = require('./ChangeCommand');

const CommandMap = {
  [COMMAND_NAME.CLICK]: ClickCommand,
  [COMMAND_NAME.CHANGE]: ChangeCommand,
}

const getCommandClass = (handle) => {
  const command_name = handle.split(':')[2];
  return CommandMap[command_name];
}

module.exports = {
  getCommandClass,
};
