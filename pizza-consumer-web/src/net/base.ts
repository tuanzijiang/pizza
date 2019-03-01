import proto from './proto.json';
import { Root as protobufRoot } from 'protobufjs';
import { reqProto, respProto, Command, CommandReq, CommandResp, reqUrl } from './Command';

const BASE_URL = 'http://localhost:3000/v0.0.1';

const request = async <T extends Command>
  (T: Command, ...param: CommandReq<T>): Promise<CommandResp<T>> => {
  try {
    const root = protobufRoot.fromJSON(proto);
    const reqType = root.lookupType(reqProto[T]);
    const respType = root.lookupType(respProto[T]);
    console.warn(1111, ...param);
    const postBuffer = reqType.encode(reqType.create(...param)).finish();
    console.warn(1111, postBuffer);

    fetch(reqUrl[T], {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-protobuf',
      },
      body: postBuffer,
    }).then(async res => {
      const resBuffer = await res.arrayBuffer();
      const result = respType.decode(new Uint8Array(resBuffer));
      return Promise.resolve(result);
    }).catch(error => console.log('Error:', error));
  } catch (e) {
    this.printLarkError(e.code);
    return Promise.reject(e);
  }
};

const protoNet = async () => {
  const root = protobufRoot.fromJSON(proto);
  const protoType = root.lookupType(respProto[Command.FETCH_USER]);

  const test = {
    userId: 1,
  };

  console.warn(9999, await request(Command.FETCH_USER, test));

  // const UserProto = root.lookupType('user.User');
  // const loginReqProto = root.lookupType('user.LoginReq');

  // console.warn(1111, User.decode(User.encode(User.fromJS())));

  // const loginReq = {
  //   type: 'PASSWORD',
  //   account: 'account',
  //   password: 'password',
  // };

  // console.warn(1111, user, loginReq);
  // const infoEncodeMsg = UserProto.encode(UserProto.create(user)).finish();
  // const logininfoEncodeMsg = loginReqProto.encode(loginReqProto.create(loginReq)).finish();
  // console.warn(2222, infoEncodeMsg, loginReqProto);
  // const infoUnSerialized = UserProto.decode(infoEncodeMsg);
  // const logininfoUnSerialized = loginReqProto.decode(logininfoEncodeMsg);
  // console.warn(3333, infoUnSerialized, logininfoUnSerialized);
};

export default {
  protoNet,
};
