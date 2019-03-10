import {Component, OnInit, ViewChild} from '@angular/core';
import {YesterdayOrder} from "../../../modules/order/yesterdayOrder";
import {OrderCount} from "../../../modules/order/orderCount";
import {Table} from "primeng/table";

@Component({
  selector: 'app-order-count',
  templateUrl: './order-count.component.html',
  styleUrls: ['./order-count.component.scss']
})
export class OrderCountComponent implements OnInit {

  @ViewChild('myTable') private _table: Table;
  yesterdayOrder: YesterdayOrder;
  orderCount: any;
  cols: any[];
  dateFilters: any;

  constructor() {
  }

  ngOnInit() {
    var _self = this;
    this.yesterdayOrder = {
      new_order_num: 100,
      complete_num: 80,
      total_amount: 800
    };

    this.orderCount = [
      {date: new Date('2019-03-04'), order_num: 1000, complete_num: 900, cancle_num: 100, total_amount: 20000, aaa: 'bbb'},
      {date: new Date('2019-03-05'), order_num: 1500, complete_num: 1300, cancle_num: 200, total_amount: 50000, aaa: 'bbb'},
      {date: new Date('2019-03-06'), order_num: 1000, complete_num: 1400, cancle_num: 150, total_amount: 50000, aaa: 'bbb'},
      {date: new Date('2019-03-07'), order_num: 2000, complete_num: 1500, cancle_num: 500, total_amount: 30000, aaa: 'bbb'},
    ];

    this.cols = [
      { field: 'date', header: '时间' },
      { field: 'order_num', header: '订单总数' },
      { field: 'complete_num', header: '已完成数' },
      { field: 'cancle_num', header: '取消数' },
      { field: 'total_amount', header: '累计金额（元）' }
    ];

    // this will be called from your templates onSelect event
    this._table.filterConstraints['dateRangeFilter'] = (value, filter): boolean => {
      // get the from/start value
      var s = _self.dateFilters[0].getTime();
      var e;
      // the to/end value might not be set
      // use the from/start date and add 1 day
      // or the to/end date and add 1 day
      if (_self.dateFilters[1]) {
        e = _self.dateFilters[1].getTime() + 86400000;
      } else {
        e = s + 86400000;
      }
      // compare it to the actual values
      return value.getTime() >= s && value.getTime() <= e;
    }
  }



}
