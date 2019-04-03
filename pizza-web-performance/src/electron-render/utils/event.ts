import { ipcRenderer } from 'electron';

let handle: (jsonParam: string) => void;

export enum PREFIX_NAME {
  // 与渲染进程的交互
  RENDER_FROM_MAIN = 'RENDER_FROM_MAIN',
  RENDER_FROM_RENDER = 'RENDER_FROM_RENDER',

  // 与WebLoader相关的交互
  WEBLOADER_FROM_MAIN = 'WEBLOADER_FROM_MAIN',
  WEBLOADER_FROM_WEBLOADER = 'WEBLOADER_FROM_WEBLOADER',

  // 系统日志
  SYSTEM_WARN = 'SYSTEM_WARN',
  SYSTEM_ERROR = 'SYSTEM_ERROR',
}

export enum COMMAND_FROM_RENDER {
  RECORD = 'RECORD',
  PLAY = 'PLAY',
  TEST = 'TEST',
}

export enum COMMAND_FROM_WEBLOADER {
  RECORD = 'RECORD',
  TEST = 'TEST',

  INFO = 'INFO',
}

export const send = (name: COMMAND_FROM_RENDER, args: any) => {
  const param = {
    args,
    name,
  };
  const jsonParam = JSON.stringify(param);

  console.log(`[${PREFIX_NAME.RENDER_FROM_RENDER}]: `, jsonParam);
  ipcRenderer.send(PREFIX_NAME.RENDER_FROM_RENDER, jsonParam);
};

export const on = (func: (jsonParam: string) => void) => {
  handle = func;
};

ipcRenderer.on(PREFIX_NAME.RENDER_FROM_MAIN, (
  event: Event, jsonParam: string,
) => {
  console.log(`[${PREFIX_NAME.RENDER_FROM_MAIN}]: `, JSON.parse(jsonParam));
  if (handle) {
    handle(jsonParam);
  }
});
