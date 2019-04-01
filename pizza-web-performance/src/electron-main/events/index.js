const { ipcMain } = require('electron');
const { getExecFunc } = require('../command');

class ElectronEvent {
  constructor(browser, forked) {
    this.browser = browser;
    this.forked = forked;
  }

  on() {
    ipcMain.on('electron_render', (event, commandName, args) => {
      console.log('[electron render]', commandName, args);
      const exec = getExecFunc(commandName);
      if(!exec) {
        console.error('[err]: exec not exist');
        return;
      }
      exec({
        forked: this.forked,
      }, args);
    });
  }

  send(commandName, args = []) {
    console.log('[electron main]', commandName, args);
    this.browser.webContents.send('electron_main', commandName, args);
  }
}

module.exports = ElectronEvent;