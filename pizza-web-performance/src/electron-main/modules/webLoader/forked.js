const process = require('process');
const { handleRecord } = require('./event');
const { COMMAND_FROM_RENDER, COMMAND_FROM_WEBLOADER, PREFIX_NAME } = require('../../config');

process.on('message', (jsonParam) => {
  const param = JSON.parse(jsonParam);
  const { name, args } = param;
  switch(name) {
    case COMMAND_FROM_RENDER.RECORD: {
      handleRecord(args, (record) => {
        const param = {
          name: COMMAND_FROM_WEBLOADER.RECORD,
          args: record,
        }
        process.send(JSON.stringify(param));
      });
      break;
    }
    default: {
      break;
    }
  }
});

