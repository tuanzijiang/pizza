import net, { Command } from '@net/base';
import { FetchPhoneReq, FetchPhoneResp } from '@src/net/api-fetch-phone';

export const fetchPhoneApi = async (param: FetchPhoneReq) => {
  const resp = await net.request(Command.FETCH_PHONE, param);
  const { resultType } = resp as FetchPhoneResp;

  if (resultType) {
    const {
      deliverymanPhone,
      shopPhone,
      servicePhone,
    } = resp as FetchPhoneResp;
    return {
      deliverymanPhone: deliverymanPhone || '15317315333',
      shopPhone: shopPhone || '18823909090',
      servicePhone: servicePhone || '800-8888-888',
    };
  }

  return false;
};
