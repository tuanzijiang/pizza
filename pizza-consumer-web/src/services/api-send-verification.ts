import net, { Command } from '@net/base';
import { SendVerificationReq } from '@src/net/api-send-verification';

export const sendVerificationApi = async (param: SendVerificationReq) => {
  await net.request(Command.SEND_VERIFICATION, param);
};
