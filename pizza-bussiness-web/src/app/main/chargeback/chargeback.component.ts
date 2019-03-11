import {Component, OnInit, ViewChild} from '@angular/core';
import {ChargebackPending} from "../../modules/chargeback/chargebackPending";
import {ChargebackProcessed} from "../../modules/chargeback/chargebackProcessed";
import {Table} from "primeng/table";

@Component({
  selector: 'app-chargeback',
  templateUrl: './chargeback.component.html',
  styleUrls: ['./chargeback.component.scss']
})
export class ChargebackComponent implements OnInit {

  @ViewChild('dtProcessed') private _table: Table;
  pendingCols: any[];
  processedCols: any[];
  chargebackPendings: ChargebackPending[];
  chargebackPro: ChargebackProcessed[];
  dateFilters: any;

  constructor() { }

  ngOnInit() {
    var _self = this;
    this.pendingCols = [
      {field: 'order_id', header: '订单编号'},
      {field: 'receiver', header: '收件人'},
      {field: 'phone', header: '联系方式'},
      {field: 'ommit_time', header: '订单建立时间'},
      {field: 'cancel_time', header: '请求取消时间'},
      {field: 'duration', header: '已支付时长'},
      {field: 'status', header: '状态'},
    ];

    this.processedCols = [
      {field: 'order_id', header: '订单编号'},
      {field: 'receiver', header: '收件人'},
      {field: 'phone', header: '联系方式'},
      {field: 'commit_time', header: '订单建立时间'},
      {field: 'cancel_time', header: '请求取消时间'},
      {field: 'duration', header: '已支付时长'},
      {field: 'result', header: '审核结果'},
    ];

    this.chargebackPendings = [
      {order_id: 'HGHH37346387', receiver: '张三', phone: '15284838485', commit_time: new Date('2019-3-12 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', status: '配送中'},
      {order_id: 'KKLO82949574', receiver: '李四', phone: '15227464645', commit_time: new Date('2019-3-11 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', status: '配送中'},
      {order_id: 'HCDE24516152', receiver: '王五', phone: '15248574744', commit_time: new Date('2019-3-10 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', status: '配送中'},
      {order_id: 'RQTW27447364', receiver: '赵六', phone: '15210293020', commit_time: new Date('2019-3-09 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', status: '配送中'},
    ];

    this.chargebackPro = [
      {order_id: 'HGHH37346387', receiver: '张三', phone: '15284838485', commit_time: new Date('2019-3-12 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', result: '通过'},
      {order_id: 'KKLO82949574', receiver: '李四', phone: '15227464645', commit_time: new Date('2019-3-11 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', result: '通过'},
      {order_id: 'HCDE24516152', receiver: '王五', phone: '15248574744', commit_time: new Date('2019-3-10 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', result: '未通过'},
      {order_id: 'RQTW27447364', receiver: '赵六', phone: '15210293020', commit_time: new Date('2019-3-09 18:42:44'), cancel_time: new Date('2019-3-12 18:43:45'), duration: '1min', result: '通过'},
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

  agreeRequest(pc: ChargebackPending) {
  }

  disagreeRequest(pc: ChargebackPending) {
  }

}
