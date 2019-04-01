const Command = require('./Command');
const { COMMAND_NAME } = require('./config');

class ChangeCommand extends Command {
  constructor(props) {
    super(props);
    const { value } = props;
    this.value = value;
  }

  static generate(clickStr) {
    const param = clickStr.substr(9);
    const { selector, value } = JSON.parse(param);
    return new ChangeCommand({
      selector,
      value,
      name: COMMAND_NAME.CHANGE,
      timestamp: new Date().valueOf(),
    });
  }
}

module.exports = ChangeCommand;
