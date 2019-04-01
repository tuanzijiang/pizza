const Command = require('./Command');
const { COMMAND_NAME } = require('./config');

class ClickCommand extends Command {
  constructor(props) {
    super(props);
  }

  static generate(clickStr) {
    const param = clickStr.substr(9);
    const { selector } = JSON.parse(param);
    return new ClickCommand({
      selector,
      name: COMMAND_NAME.CLICK,
      timestamp: new Date().valueOf(),
    });
  }
}

module.exports = ClickCommand;
