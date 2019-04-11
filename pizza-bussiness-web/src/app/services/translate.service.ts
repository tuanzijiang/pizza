import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TranslateService {

  constructor() {}
  public orderTranslate = {
    orderId: '订单号',
    receiveName: '收货人名称',
    receivePhone: '收货人电话',
    receiveAddress: '收货地址',
    name: '商品名称',
    price: '单价',
    count: '数量',
    totalAmount: '订单总金额',
    buyPhone: '购买人电话',
    commitTime: '下单时间',
    shopId: '商家ID',
    driverId: '配送员ID',
    startDeliverTime: '开始配送时间',
    arriveTime: '送达时间',
    state: '状态'
  }
}
