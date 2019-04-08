import {Component, OnInit, ViewChild} from '@angular/core';
import {ChargebackPending} from "../../modules/chargeback/chargebackPending";
import {ChargebackProcessed} from "../../modules/chargeback/chargebackProcessed";
import {Table} from "primeng/table";
import {ChargebackService} from "../../services/chargeback/chargeback.service";
import {BaseResponse} from "../../modules/baseResponse";
import {OrderService} from "../../services/order/order.service";

var _self = this;
@Component({
  selector: 'app-chargeback',
  templateUrl: './chargeback.component.html',
  styleUrls: ['./chargeback.component.scss']
})
export class ChargebackComponent implements OnInit {

  @ViewChild('dtProcessed') private _table: any;
  pendingCols: any[];
  processedCols: any[];
  chargebackPendings: ChargebackPending[];
  chargebackPro: ChargebackProcessed[];
  dateFilters: any;
  showPage: boolean;

  constructor(private chargebackService: ChargebackService, private orderService: OrderService) { }

  ngOnInit() {
    this.showPage = false;

    this.pendingCols = [
      {field: 'orderId', header: '订单编号'},
      {field: 'receiveName', header: '收件人'},
      {field: 'receivePhone', header: '联系方式'},
      {field: 'commitTime', header: '订单建立时间'},
      {field: 'paidPeriod', header: '已支付时长'},
      {field: 'state', header: '状态'},
    ];

    this.processedCols = [
      {field: 'orderId', header: '订单编号'},
      {field: 'receiveName', header: '收件人'},
      {field: 'receivePhone', header: '联系方式'},
      {field: 'commitTime', header: '订单建立时间'},
      {field: 'paidPeriod', header: '已支付时长'},
      {field: 'state', header: '审核结果'},
    ];

    this.chargebackService.getPendingList().subscribe(
      (pendingList: ChargebackPending[]) => {
        this.chargebackPendings = pendingList;
        this.getProcessedOrder();
      }
    );
  }

  getProcessedOrder() {
    this.orderService.getCancelOrderList().subscribe(
      (cp: ChargebackProcessed[]) => {
        this.chargebackPro = cp;
        this.showPage = true;
        this.setTable();
      }
    )
  }

  agreeRequest(pc: ChargebackPending) {
    this.chargebackService.allowOrder(pc.orderId).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if (this.chargebackPendings.find(obj => obj.orderId == pc.orderId) != null) {
            this.chargebackPendings = this.chargebackPendings.filter(obj => obj.orderId != pc.orderId);
          }
        }
      }
    )
  }

  disagreeRequest(pc: ChargebackPending) {
    this.chargebackService.denyOrder(pc.orderId).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if (this.chargebackPendings.find(obj => obj.orderId == pc.orderId) != null) {
            this.chargebackPendings = this.chargebackPendings.filter(obj => obj.orderId != pc.orderId);
          }
        }
      }
    )
  }

  setTable() {
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
