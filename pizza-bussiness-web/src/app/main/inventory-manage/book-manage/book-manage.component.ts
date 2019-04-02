import {Component, OnInit} from '@angular/core';
import {BookAlert} from "../../../modules/inventory-manage/book-alert";
import {InventoryManageService} from "../../../services/inventory-manage/inventory-manage.service";
import {BuyIngredient} from "../../../modules/inventory-manage/buy-ingredient";
import {BaseResponse} from "../../../modules/baseResponse";

@Component({
  selector: 'app-book-manage',
  templateUrl: './book-manage.component.html',
  styleUrls: ['./book-manage.component.scss']
})
export class BookManageComponent implements OnInit {

  cols: any[];
  alerts: BookAlert[];

  constructor(private inventoryManageService: InventoryManageService) {
  }

  ngOnInit() {
    this.inventoryManageService.getAlarmList().subscribe(
      (alerts: BookAlert[]) => {
        this.alerts = alerts;
      }
    );
    this.cols = [
      {field: 'name', header: '原料名称'},
      {field: 'shopName', header: '工厂名'},
      {field: 'count', header: '当前库存值'},
      {field: 'alertNum', header: '该原料阈值'},
      {field: 'orderNum', header: '订购份数(可修改)'},
    ];

  }

  confirmBook(order: BookAlert) {
    let ingredient = new BuyIngredient();
    ingredient.ingredientId = order.id;
    ingredient.shopId = order.shopId;
    ingredient.orderNum = order.orderNum;
    this.inventoryManageService.buyIngredient(ingredient).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if (this.alerts.find(obj => obj.id == order.id) != null) {
            this.alerts = this.alerts.filter(obj => obj.id != order.id);
          }
        }
      }
    );
  }

  cancelBook(order: BookAlert) {
    if (this.alerts.find(obj => obj.id == order.id) != null) {
      this.alerts = this.alerts.filter(obj => obj.id != order.id);
    }
  }
}
