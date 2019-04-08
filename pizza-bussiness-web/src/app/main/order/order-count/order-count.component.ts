import {AfterViewChecked, AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {YesterdayOrder} from "../../../modules/order/yesterdayOrder";
import {Table} from "primeng/table";
import {OrderService} from "../../../services/order/order.service";
import {OrderCount} from "../../../modules/order/orderCount";
import {OrderCountDate} from "../../../modules/order/orderCountDate";
import {DataTable} from "primeng/primeng";

@Component({
  selector: 'app-order-count',
  templateUrl: './order-count.component.html',
  styleUrls: ['./order-count.component.scss']
})
export class OrderCountComponent implements OnInit, AfterViewInit {

  @ViewChild('myTable') table: Table;
  yesterdayOrder: YesterdayOrder;
  orderCountList: OrderCount[];
  cols: any[];
  dateFilters: any;
  showOrderCountPage: boolean;

  constructor(private orderService: OrderService) {
  }

  ngOnInit() {
    this.orderCountList = [];
    this.showOrderCountPage = false;
    this.cols = [
      { field: 'date', header: '时间' },
      { field: 'orderNum', header: '订单总数' },
      { field: 'completeNum', header: '已完成数' },
      { field: 'cancelNum', header: '取消数' },
      { field: 'totalAmount', header: '累计金额（元）' }
    ];

    this.orderService.getYesterdayOrder().subscribe(
      (yesOrder: YesterdayOrder) => {
        this.yesterdayOrder = yesOrder;
        this.getOrderCount();
      }
    );
  }

  ngAfterViewInit() {
    // this will be called from your templates onSelect event
    this.table.filterConstraints['dateRangeFilter'] = (value, filter): boolean => {
      // get the from/start value
      let s = this.dateFilters[0].getTime();
      let e;
      // the to/end value might not be set
      // use the from/start date and add 1 day
      // or the to/end date and add 1 day
      if (this.dateFilters[1]) {
        e = this.dateFilters[1].getTime() + 86400000;
      } else {
        e = s + 86400000;
      }
      // compare it to the actual values
      return value.getTime() >= s && value.getTime() <= e;
    };
  }

  getOrderCount() {
    let orderCountDate = new OrderCountDate();
    orderCountDate.startDate = '2019/01/01';
    orderCountDate.endDate = '2019/04/30';
    this.orderService.getOrderCountStatus(orderCountDate).subscribe(
      (orderCountList: OrderCount[]) => {
        for(let orderCount of orderCountList) {
          let newOrder = new OrderCount();
          newOrder.date = new Date(orderCount.date);
          newOrder.orderNum = orderCount.orderNum;
          newOrder.completeNum = orderCount.completeNum;
          newOrder.cancelNum = orderCount.cancelNum;
          newOrder.totalAmount = orderCount.totalAmount;
          this.orderCountList.push(newOrder);
        }
        this.showOrderCountPage = true;
      }
    );
  };


}
