import proto from './proto.json';
import { ip_4 } from '@utils/device';
import { info, LogTag, error } from '@utils/log';
import { Root as protobufRoot } from 'protobufjs';
import { Command, CommandReq, CommandResp } from './command';
import { reqProto, reqUrl, respProto } from './config';
export { Command } from './command';

const BASE_URL = `http://139.224.37.12:3000/`;
// const BASE_URL = `http://${ip_4}:3000/`;

const request = async <T extends Command>
  (T: Command, ...param: CommandReq<T>): Promise<CommandResp<T>> => {
  try {
    info(LogTag.NET, `${T} start`, param);
    const root = protobufRoot.fromJSON(proto);
    const reqType = root.lookupType(reqProto[T]);
    const postBuffer = reqType.encode(reqType.create(...param)).finish();
    const url = `${BASE_URL}${reqUrl[T]}`;

    return new Promise((resolve, reject) => {
      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-protobuf',
        },
        body: postBuffer,
      }).then(async res => {
        const resBuffer = await res.arrayBuffer();
        const respType = root.lookupType(respProto[T]);
        const result: unknown  = respType.decode(new Uint8Array(resBuffer));
        info(LogTag.NET, `${T} success`, result);
        resolve(result as CommandResp<T>);
      }).catch(e => {
        error(LogTag.NET, `${T} error`, e);
        reject(error);
      });
    });
  } catch (e) {
    error(LogTag.NET, `${T} error`, e);
    return Promise.reject(e);
  }
};

export default {
  request,
};
