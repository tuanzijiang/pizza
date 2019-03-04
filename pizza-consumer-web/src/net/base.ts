import proto from './proto.json';
import { info, LogTag, error } from '@utils/log';
import { Root as protobufRoot } from 'protobufjs';
import { Command, CommandReq, CommandResp } from './command';
import { reqProto, reqUrl, respProto } from './config';

const BASE_URL = 'http://localhost:3000/';

const request = async <T extends Command>
  (T: Command, ...param: CommandReq<T>): Promise<CommandResp<T>> => {
  try {
    info(LogTag.NET, `${T} start`, param);
    const root = protobufRoot.fromJSON(proto);
    const reqType = root.lookupType(reqProto[T]);
    const postBuffer = reqType.encode(reqType.create(...param)).finish();
    const url = `${BASE_URL}${reqUrl[T]}`;

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-protobuf',
      },
      body: postBuffer,
    }).then(async res => {
      const resBuffer = await res.arrayBuffer();
      const respType = root.lookupType(respProto[T]);
      const result = respType.decode(new Uint8Array(resBuffer));
      info(LogTag.NET, `${T} success`, result);
      return Promise.resolve(result);
    }).catch(e => {
      error(LogTag.NET, `${T} error`, e);
      return Promise.reject(error);
    });
  } catch (e) {
    error(LogTag.NET, `${T} error`, e);
    this.printLarkError(e.code);
    return Promise.reject(e);
  }
};

export default {
  request,
};
