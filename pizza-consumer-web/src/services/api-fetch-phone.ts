import net, { Command } from '@net/base';
import { FetchPhoneReq, FetchPhoneResp } from '@src/net/api-fetch-phone';

export const fetchPhoneApi = async (param: FetchPhoneReq) => {
  const resp = await net.request(Command.FETCH_PHONE, param);
  const { resultType } = resp as FetchPhoneResp;

  if (resultType) {
    return resp;
  }

  return false;
};
