import { Component, OnInit } from '@angular/core';
import {Delivery} from "../../modules/delivery/delivery";

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
  displayDialog: boolean;

  constructor() { }

  ngOnInit() {
    this.dialogHeader = '';
    this.displayDialog = false;
    this.cols = [
      {field: 'id', header: '配送员ID'},
      {field: 'name', header: '姓名'},
      {field: 'phone', header: '手机号'},
      {field: 'shop_id', header: '绑定的店ID'},
    ];

    this.deliveries = [
      {id: 'GDH72636', name: '迪丽热巴', phone: '15284736374', shop_id: 'AAAA'},
      {id: 'FHD47547', name: '古力娜扎', phone: '15248738485', shop_id: 'BBBB'},
      {id: 'LFI57564', name: '关晓彤', phone: '15284857464', shop_id: 'CCCC'},
      {id: 'JDB58392', name: '赵丽颖', phone: '15269506968', shop_id: 'DDDDD'},
    ]
  }

  addDelivery() {
    this.tempDel = new Delivery();
    this.dialogHeader = '新增配送员';
    this.displayDialog = true;
  }

  onRowCancel(del: Delivery) {
    if(this.deliveries.find(obj => obj.id == del.id) != null) {
      this.deliveries = this.deliveries.filter(obj => obj.id != del.id);
    }
  }

  editDelivery(del: Delivery) {
    this.dialogHeader = '配送员基本信息修改';
    this.tempDel = del;
    this.displayDialog = true;
  }

  submitChangedDel() {
    this.displayDialog = false;
    this.tempDel = null;
  }

  submitAddedDel() {
    this.displayDialog = false;
    this.tempDel = null;
  }

  closeDialog() {
    this.displayDialog = false;
    this.tempDel = null;
  }


}
