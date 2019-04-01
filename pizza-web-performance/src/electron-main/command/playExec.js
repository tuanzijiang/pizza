const playExec = (ev, args) => {
  const { forked } = ev;

  const param = {
    command: 'PLAY',
    args: [],
  };

  forked.send(JSON.stringify(param));
}

module.exports = {
  playExec,
}