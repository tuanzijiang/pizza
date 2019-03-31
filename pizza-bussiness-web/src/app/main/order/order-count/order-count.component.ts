import {Component, OnInit, ViewChild} from '@angular/core';
import {YesterdayOrder} from "../../../modules/order/yesterdayOrder";
import {Table} from "primeng/table";
import {OrderService} from "../../../services/order/order.service";
import {OrderCount} from "../../../modules/order/orderCount";

@Component({
  selector: 'app-order-count',
  templateUrl: './order-count.component.html',
  styleUrls: ['./order-count.component.scss']
})
export class OrderCountComponent implements OnInit {

  @ViewChild('myTable') private _table: Table;
  yesterdayOrder: YesterdayOrder;
  orderCountList: OrderCount[];
  cols: any[];
  dateFilters: any;

  constructor(private orderService: OrderService) {
  }

  ngOnInit() {
    var _self = this;
    this.orderService.getYesterdayOrder().subscribe(
      (yesOrder: YesterdayOrder) => {
        this.yesterdayOrder = yesOrder;
      }
    );

    this.orderService.getOrderCountStatus().subscribe(
      (orderCountList: OrderCount[])=> {
        this.orderCountList = [];
        for(let orderCount of orderCountList) {
          let newOrder = new OrderCount();
          newOrder.date = new Date(orderCount.date);
          newOrder.orderNum = orderCount.orderNum;
          newOrder.completeNum = orderCount.completeNum;
          newOrder.cancelNum = orderCount.cancelNum;
          newOrder.totalAmount = orderCount.totalAmount;
          this.orderCountList.push(newOrder);
        }
      }
    );

    this.cols = [
      { field: 'date', header: '时间' },
      { field: 'orderNum', header: '订单总数' },
      { field: 'completeNum', header: '已完成数' },
      { field: 'cancelNum', header: '取消数' },
      { field: 'totalAmount', header: '累计金额（元）' }
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
