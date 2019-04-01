class Command {
  constructor(props) {
    const { name, timestamp, selector } = props;
    this.name = name;
    this.timestamp = timestamp;
    this.selector = selector;
  }

  static generate() {
    return new Command();
  }
}

module.exports = Command;
