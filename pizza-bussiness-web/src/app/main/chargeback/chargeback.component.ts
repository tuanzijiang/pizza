import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ChargebackPending} from "../../modules/chargeback/chargebackPending";
import {ChargebackProcessed} from "../../modules/chargeback/chargebackProcessed";
import {Table} from "primeng/table";
import {ChargebackService} from "../../services/chargeback/chargeback.service";
import {BaseResponse} from "../../modules/baseResponse";
import {OrderService} from "../../services/order/order.service";

@Component({
  selector: 'app-chargeback',
  templateUrl: './chargeback.component.html',
  styleUrls: ['./chargeback.component.scss']
})
export class ChargebackComponent implements OnInit, AfterViewInit {

  @ViewChild('dtProcessed') table: Table;
  pendingCols: any[];
  processedCols: any[];
  chargebackPendings: ChargebackPending[];
  chargebackPro: ChargebackProcessed[];
  dateFilters: any;
  showPage: boolean;

  constructor(private chargebackService: ChargebackService, private orderService: OrderService) { }

  ngOnInit() {
    this.showPage = false;
    this.chargebackPendings = [];
    this.chargebackPro = [];
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

    this.getPendingList();
  }

  refreshpage() {
    this.getPendingList();
  }

  ngAfterViewInit(): void {
    // this will be called from your templates onSelect event
    this.table.filterConstraints['dateRangeFilter'] = (value, filter): boolean => {
      // get the from/start value
      let s = this.dateFilters[0].getTime();
      let e;
      // the to/end value might not be set
      // use the from/start date and add 1 day
      // or the to/end date and add 1 day
      if (this.dateFilters[1]) {
        e = this.dateFilters[1].getTime();
      } else {
        e = s;
      }
      // compare it to the actual values
      return value.getTime() >= s && value.getTime() <= e;
    };
  }

  getPendingList() {
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
        this.chargebackPro = [];
        for(let processedItem of cp) {
          let item = new ChargebackProcessed();
          item.orderId = processedItem.orderId;
          item.receiveName = processedItem.receiveName;
          item.receivePhone = processedItem.receivePhone;
          item.paidPeriod = processedItem.paidPeriod;
          item.state = processedItem.state;
          item.commitTime = new Date(processedItem.commitTime);
          this.chargebackPro.push(item);
        }
        this.showPage = true;
      }
    )
  }

  agreeRequest(pc: ChargebackPending) {
    this.chargebackService.allowOrder(pc.orderId).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.showPage = false;
          if (this.chargebackPendings.find(obj => obj.orderId == pc.orderId) != null) {
            this.chargebackPendings = this.chargebackPendings.filter(obj => obj.orderId != pc.orderId);
          }
          this.refreshpage();
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
          this.showPage = false;
          if (this.chargebackPendings.find(obj => obj.orderId == pc.orderId) != null) {
            this.chargebackPendings = this.chargebackPendings.filter(obj => obj.orderId != pc.orderId);
          }
          this.refreshpage();
        }
      }
    )
  }

}
