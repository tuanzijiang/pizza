import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TranslateService {

  constructor() {}
  public orderDetails = {
    order_id: '订单号',
    receive_name: '收货人名称',
    receive_phone: '收货人电话',
    receive_address: '收货地址',
    menu_name: '商品名称',
    menu_price: '单价',
    menu_count: '数量',
    total_amount: '订单总金额',
    buy_phone: '购买人电话',
    commit_time: '下单时间',
    shop_id: '商家ID',
    driver_id: '配送员ID',
    start_deliver_time: '开始配送时间',
    arrive_time: '送达时间',
    state: '状态'
  }
}
