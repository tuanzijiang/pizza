import net, { BaseRequest, BaseResponse } from '@net/base';
import log from '@utils/log';
import { ActivityEntity } from '@src/store/reducers/entity/schema';

export interface FetchActivityRequest extends BaseRequest {
  keyword: string;
  pageIdx: number;
}

export interface FetchActivityResponse extends BaseResponse {
  response: ActivityEntity[];
}

export const fetchActivity: (request: FetchActivityRequest) => Promise<FetchActivityResponse> =
  async (request) => {
    const url = 'activity';
    try {
      log.info('[net] fetchActivity start');
      const response = await net.get(url, request);
      log.info('[net] fetchActivity success', response);
      return {
        response,
      };
    } catch (err) {
      log.error('[net] fetchActivity error', err);
    }
  };
