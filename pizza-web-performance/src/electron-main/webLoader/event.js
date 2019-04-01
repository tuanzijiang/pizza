const EVENT_NAME = {
  CLICK: 'CLICK',
  CHANGE: 'CHANGE',
}

const EVENT_BIND_FUNC = {
  [EVENT_NAME.CLICK]: () => {
    document.body.addEventListener('click', (ev) => {
      const selector = __LOADER__.fetchSelector(ev.target);
      window.__LOADER__.sendCommand(window.__LOADER__.COMMAND_NAME.CLICK, JSON.stringify({
        selector,
      }));
    });
  },
  [EVENT_NAME.CHANGE]: () => {
    document.body.addEventListener('change', (ev) => {
      const selector = __LOADER__.fetchSelector(ev.target);
      const value = ev.target.value;
      window.__LOADER__.sendCommand(window.__LOADER__.COMMAND_NAME.CHANGE, JSON.stringify({
        selector,
        value,
      }));
    })
  }
}

module.exports = {
  EVENT_NAME,
  EVENT_BIND_FUNC,
}