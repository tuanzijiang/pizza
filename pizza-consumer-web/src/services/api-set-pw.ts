import net, { Command } from '@net/base';
import { SetPWReq } from '@src/net/api-set-pw';

export const setPWApi = async (param: SetPWReq) => {
  await net.request(Command.SET_PW, param);
};
