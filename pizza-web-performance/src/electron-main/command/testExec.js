const testExec = (ev, args) => {
  const { forked } = ev;

  const param = {
    command: 'TEST',
    args: [],
  };

  forked.send(JSON.stringify(param));
}

module.exports = {
  testExec,
}