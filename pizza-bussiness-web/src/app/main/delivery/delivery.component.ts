import {Component, OnInit} from '@angular/core';
import {Delivery} from "../../modules/delivery/delivery";
import {DeliveryService} from "../../services/delivery/delivery.service";
import {BaseResponse} from "../../modules/baseResponse";

@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.scss']
})
export class DeliveryComponent implements OnInit {

  cols: any[];
  deliveries: Delivery[];
  tempDel: Delivery;
  dialogHeader: string;
  displayChangeDialog: boolean;
  displayAddDialog: boolean;
  showPage: boolean;

  constructor(private deliveryService: DeliveryService) {
  }

  ngOnInit() {
    this.showPage = false;
    this.dialogHeader = '';
    this.displayChangeDialog = false;
    this.displayAddDialog = false;

    this.getDriverList();

    this.cols = [
      {field: 'id', header: '配送员ID'},
      {field: 'name', header: '姓名'},
      {field: 'phone', header: '手机号'},
      {field: 'shopId', header: '绑定的店ID'},
    ];

  }

  getDriverList() {
    this.deliveryService.getDriverList().subscribe(
      (driverList: Delivery[]) => {
        this.deliveries = driverList;
        this.showPage = true;
      }
    );
  }

  addDelivery() {
    this.tempDel = new Delivery();
    this.dialogHeader = '新增配送员';
    this.displayAddDialog = true;
  }

  onRowCancel(del: Delivery) {
    this.deliveryService.removeDriver(del.id).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if (this.deliveries.find(obj => obj.id == del.id) != null) {
            this.deliveries = this.deliveries.filter(obj => obj.id != del.id);
          }
        }
      }
    );
  }

  editDelivery(del: Delivery) {
    this.dialogHeader = '配送员基本信息修改';
    this.tempDel = del;
    this.displayChangeDialog = true;
  }

  submitChangedDel() {
    this.deliveryService.editDriver(this.tempDel).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayChangeDialog = false;
          this.showPage = false;
          this.tempDel = null;
          this.getDriverList();
        }
      }
    );
  }

  submitAddedDel() {
    this.deliveryService.addDriver(this.tempDel).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayAddDialog = false;
          this.showPage = false;
          this.tempDel = null;
          this.getDriverList();
        }
      }
    );
  }

  closeDialog() {
    this.displayChangeDialog = false;
    this.displayAddDialog = false;
    this.tempDel = null;
  }


}
