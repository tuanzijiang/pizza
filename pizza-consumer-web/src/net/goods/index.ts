import net, { BaseRequest, BaseResponse } from '@net/base';
import log from '@utils/log';
import { GoodsGroupEntity, GoodsEntity } from '@src/store/reducers/entity/schema';

export interface FetchGroupRequest extends BaseRequest { }

export interface FetchGroupResponse extends BaseResponse {
  response: GoodsGroupEntity[];
}

export const fetchGroup: (request?: FetchGroupRequest) => Promise<FetchGroupResponse>
  = async (request) => {
    const url = 'goods/group';
    try {
      log.info('[net] fetchGoodsGroup start');
      const response = await net.get(url, request);
      log.info('[net] fetchGoodsGroup success', response);
      return {
        response,
      };
    } catch (err) {
      log.error('[net] fetchGoodsGroup error', err);
    }
  };

export interface FetchGoodsRequest extends BaseRequest {
  group_id: string;
  keyword: string;
  pageIdx: number;
}

export interface FetchGoodsResponse extends BaseResponse {
  response: GoodsEntity[];
}

export const fetchGoods: (request: FetchGoodsRequest) => Promise<FetchGoodsResponse>
  = async (request) => {
    const url = 'goods/goods';
    try {
      log.info('[net] fetchGoods start');
      const response = await net.get(url, request);
      log.info('[net] fetchGoods success', response);
      return {
        response,
      };
    } catch (err) {
      log.error('[net] fetchGoods error', err);
    }
  };
