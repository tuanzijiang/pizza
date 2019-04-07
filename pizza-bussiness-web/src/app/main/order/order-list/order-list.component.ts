import {Component, OnInit, ViewChild} from '@angular/core';
import {OrderDetail} from "../../../modules/order/orderDetail";
import {TranslateService} from "../../../services/translate.service";
import {OrderService} from "../../../services/order/order.service";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  orders: OrderDetail[];
  cols: any[];
  states: any[];
  selectedOrder: OrderDetail;
  displayDetail: boolean;
  displayPage: boolean;
  orderListKeys: Array<string>;
  MenuListKeys: Array<string>;

  constructor(private translateService: TranslateService, private orderService: OrderService) {
  }

  ngOnInit() {
    this.displayPage = false;
    this.displayDetail = false;
    this.orderListKeys = ['receiveName', 'receivePhone', 'receiveAddress', 'totalAmount', 'menuList', 'buyPhone', 'commitTime', 'shopId', 'driverId', 'startDeliverTime', 'arriveTime', 'state'];
    this.MenuListKeys = ['name', 'price', 'count'];
    this.cols = [
      {field: 'orderId', header: '订单编号'},
      {field: 'receiveName', header: '收件人'},
      {field: 'receivePhone', header: '联系方式'},
      {field: 'receiveAddress', header: '收货地址'},
      {field: 'totalAmount', header: '订单总金额'},
      {field: 'commitTime', header: '建立时间'},
      {field: 'state', header: '状态'},
    ];
    this.states = [
      {label: '请选择状态', value: null},
      {label: '未知', value: '未知'},
      {label: '待支付', value: '待支付'},
      {label: '已支付', value: '已支付'},
      {label: '取消审核中', value: '取消审核中'},
      {label: '取消失败', value: '取消失败'},
      {label: '配送中', value: '配送中'},
      {label: '已送达', value: '已送达'},
      {label: '已完成', value: '已完成'},
      {label: '待配送', value: '待配送'},
      {label: '商家接单失败', value: '商家接单失败'},
    ];
    this.orderService.getOrders().subscribe(
      (orders: OrderDetail[]) => {
        this.orders = orders;
        console.log(this.orders);
        this.displayPage = true;
      }
    );
  }

  selectOrderWithButton(order: OrderDetail) {
    this.selectedOrder = order;
    this.displayDetail = true;
  }
}
