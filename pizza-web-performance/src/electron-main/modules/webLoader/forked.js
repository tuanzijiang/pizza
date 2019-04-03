const process = require('process');
const { handleRecord, handlePlay, handleTest } = require('./event');
const { COMMAND_FROM_RENDER, COMMAND_FROM_WEBLOADER, PREFIX_NAME } = require('../../config');

const recordMap = {};

process.on('message', (jsonParam) => {
  const param = JSON.parse(jsonParam);
  const { name, args } = param;
  switch (name) {
    case COMMAND_FROM_RENDER.RECORD: {
      handleRecord(args, (record) => {
        recordMap[record.id] = record;
        const param = {
          name: COMMAND_FROM_WEBLOADER.RECORD,
          args: record,
        }
        process.send(JSON.stringify(param));
      });
      break;
    }
    case COMMAND_FROM_RENDER.PLAY: {
      const { recordId } = args;
      const record = recordMap[recordId];

      handlePlay(record);
      break;
    }
    case COMMAND_FROM_RENDER.TEST: {
      const { recordId } = args;
      const record = recordMap[recordId];

      args.record = record;
      handleTest(args, (info) => {
        const param = {
          name: COMMAND_FROM_WEBLOADER.TEST,
          args: {
            pageIdx: info.pageIdx,
            status: info.status,
            msg: info.msg,
            detail: info.detail,
          }
        }

        process.send(JSON.stringify(param));
      });
    }
    default: {
      break;
    }
  }
});

