import {Component, OnInit, ViewChild} from '@angular/core';
import {Order} from "../../../modules/order/order";
import {OrderDetail} from "../../../modules/order/orderDetail";
import {TranslateService} from "../../../services/translate.service";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  orders: Order[];
  cols: any[];
  states: any[];
  selectedOrder: Order;
  selectedOrderDetail: OrderDetail;
  displayDetail: boolean;

  constructor(public translateService: TranslateService) {
  }

  ngOnInit() {
    this.displayDetail = false;
    this.cols = [
      {field: 'order_id', header: '订单编号'},
      {field: 'receive_name', header: '收件人'},
      {field: 'phone', header: '联系方式'},
      {field: 'receive_address', header: '收货地址'},
      {field: 'total_amount', header: '订单总金额'},
      {field: 'commit_time', header: '建立时间'},
      {field: 'state', header: '状态'},
    ];
    this.states = [
      {label: '请选择状态', value: null},
      {label: '未接单', value: '未接单'},
      {label: '配送中', value: '配送中'},
      {label: '已送达', value: '已送达'},
    ];
    this.orders = [
      {
        order_id: '1',
        receive_name: 'zhangsan',
        phone: '15216607890',
        receive_address: 'ECNU',
        total_amount: 18.5,
        commit_time: '2016-09-30 14:23:16',
        state: '未接单'
      },
      {
        order_id: '2',
        receive_name: 'lisi',
        phone: '15216658473',
        receive_address: 'Global Harber',
        total_amount: 20,
        commit_time: '2016-09-30 14:23:16',
        state: '配送中'
      },
      {
        order_id: '3',
        receive_name: 'wangwu',
        phone: '15216695860',
        receive_address: 'Changfeng Park',
        total_amount: 30.8,
        commit_time: '2016-09-30 14:23:16',
        state: '配送中'
      },
      {
        order_id: '4',
        receive_name: 'zhaoliu',
        phone: '15216616253',
        receive_address: 'Zhongshan Park',
        total_amount: 40.9,
        commit_time: '2016-09-30 14:23:16',
        state: '已送达'
      },
      {
        order_id: '5',
        receive_name: 'tianqi',
        phone: '15216612635',
        receive_address: 'iapm',
        total_amount: 105.4,
        commit_time: '2016-09-30 14:23:16',
        state: '未接单'
      }
    ];

    this.selectedOrderDetail = {
      order_id: '1',
      receive_name: 'zhangsan',
      receive_phone: '15216607890',
      receive_address: 'ECNU',
      menu: [
        {menu_name: '蔬果沙拉', menu_count: 1, menu_price: 48.5},
        {menu_name: '炸鸡', menu_count: 2, menu_price: 50}
      ],
      total_amount: 18.5,
      buy_phone: '15201715516',
      commit_time: '2016-09-30 14:23:16',
      shop_id: '123312',
      driver_id: '2131311312',
      start_deliver_time: '2016-09-30 14:25:00',
      arrive_time: '2016-09-30 14:50:00',
      state: '未接单'
    };
  }

  selectCarWithButton(order: Order) {
    this.selectedOrder = order;
    //Call order-detail function here
    //...
    this.displayDetail = true;
  }

  getKeys(item): Array<string> {
    return Object.keys(item);
  }
}
