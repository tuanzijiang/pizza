const recordExec = (ev, args) => {
  const { forked } = ev;

  const param = {
    command: 'RECORD',
    args: [],
  };

  forked.send(JSON.stringify(param));
}

module.exports = {
  recordExec,
}