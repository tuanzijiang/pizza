import { Component, OnInit } from '@angular/core';
import {BookAlert} from "../../../modules/inventory-manage/book-alert";

@Component({
  selector: 'app-book-manage',
  templateUrl: './book-manage.component.html',
  styleUrls: ['./book-manage.component.scss']
})
export class BookManageComponent implements OnInit {

  cols: any[];
  alerts: BookAlert[];

  constructor() { }

  ngOnInit() {
    this.cols = [
      {field: 'factory_name', header: '工厂名'},
      {field: 'material_name', header: '原料名称'},
      {field: 'inventory', header: '当前库存值'},
      {field: 'threshold', header: '该原料阈值'},
      {field: 'order_num', header: '订购份数(可修改)'},
    ];

    this.alerts = [
      {factory_name: '刘昊然的工厂', material_name: '面粉', inventory: 30, threshold: 40, order_num: 500},
      {factory_name: '白敬亭的工厂', material_name: '香油', inventory: 50, threshold: 55, order_num: 1000},
      {factory_name: '吴磊的工厂', material_name: '菠萝', inventory: 40, threshold: 50, order_num: 300},
      {factory_name: '佟丽娅的工厂', material_name: '鸡精', inventory: 30, threshold: 40, order_num: 500},
    ]
  }

  confirmBook(order: BookAlert) {
    alert(order.material_name + ' from ' + order.factory_name + ' confirmed!');
  }

  cancelBook(order: BookAlert) {
    alert(order.material_name + ' from ' + order.factory_name + ' canceled!');
  }
}
