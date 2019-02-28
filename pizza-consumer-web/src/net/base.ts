import log from '@utils/log';
import proto from './proto.json';
import { Root as protobufRoot } from 'protobufjs';

export enum Method {
  GET = 'GET',
  POST = 'POST',
  DELETE = 'DELETE',
  PUT = 'PUT',
}

export interface BaseRequest { }

export interface BaseResponse { }

const BASE_URL = 'http://localhost:3000/v0.0.1';

const getParams = (data: Object) =>
  Object.entries(data).reduce((prev, [key, val], idx) =>
    idx ? `${prev}&${key}=${encodeURI(val)}` : `${key}=${encodeURI(val)}`
    , '');

const base = (method: Method) =>
  (url: string, data: Object) => {
    let fetchUrl = `${BASE_URL}/${url}`;
    if (method === Method.GET) {
      fetchUrl = data ? `${fetchUrl}?${getParams(data)}` : fetchUrl;
    }

    return new Promise((resolve: (response: any) => void, reject: (err: any) => void) => {
      log.info(`[net]: fetchUrl: ${fetchUrl}`);
      fetch(fetchUrl, {
        method,
        body: method !== Method.GET ? JSON.stringify(data && {}) : null,
        mode: 'cors',
        credentials: 'omit',
        headers: {
          'content-type': 'application/json',
        },
      }).then(async (response) => {
        resolve(await response.json());
      }).catch((err) => {
        reject(err);
      });
    });
  };

const protoNet = () => {
  const root = protobufRoot.fromJSON(proto);
  const UserProto = root.lookupType('user.User');
  const loginReqProto = root.lookupType('user.LoginReq');

  const user = {
    id: 'id123',
    phone: 'phone123',
    userName: 'name123',
  };

  const loginReq = {
    type: 'PASSWORD',
    account: 'account',
    password: 'password',
  };

  console.warn(1111, user, loginReq);
  const infoEncodeMsg = UserProto.encode(UserProto.create(user)).finish();
  const logininfoEncodeMsg = loginReqProto.encode(loginReqProto.create(loginReq)).finish();
  console.warn(2222, infoEncodeMsg, loginReqProto);
  const infoUnSerialized = UserProto.decode(infoEncodeMsg);
  const logininfoUnSerialized = loginReqProto.decode(logininfoEncodeMsg);
  console.warn(3333, infoUnSerialized, logininfoUnSerialized);
};

export default {
  protoNet,
  post: base(Method.POST),
  get: base(Method.GET),
  put: base(Method.PUT),
  delete: base(Method.DELETE),
};
