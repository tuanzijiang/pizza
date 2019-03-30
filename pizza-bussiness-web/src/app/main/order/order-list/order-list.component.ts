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
  selectedOrderDetail: OrderDetail;
  displayDetail: boolean;

  constructor(private translateService: TranslateService, private orderService: OrderService) {
  }

  ngOnInit() {
    this.orders = [];
    this.displayDetail = false;
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
      {label: '未接单', value: '未接单'},
      {label: '配送中', value: '配送中'},
      {label: '已送达', value: '已送达'},
    ];
    this.orderService.getOrders().subscribe(
      (orders: OrderDetail[]) => {
        this.orders = orders;
      }
    );
  }

  selectOrderWithButton(order: OrderDetail) {
    this.selectedOrder = order;
    this.displayDetail = true;
  }

  getKeys(item): Array<string> {
    return Object.keys(item);
  }
}
