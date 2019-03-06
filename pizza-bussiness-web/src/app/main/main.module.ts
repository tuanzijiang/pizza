import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderListComponent } from './order/order-list/order-list.component';
import { OrderCountComponent } from './order/order-count/order-count.component';
import { UserManageComponent } from './system-manage/user-manage/user-manage.component';
import { MenuManageComponent } from './system-manage/menu-manage/menu-manage.component';
import { FactoryManageComponent } from './system-manage/factory-manage/factory-manage.component';
import { ChargebackComponent } from './chargeback/chargeback.component';
import { DeliveryComponent } from './delivery/delivery.component';
import { MaterialManageComponent } from './inventory-manage/material-manage/material-manage.component';
import { BookManageComponent } from './inventory-manage/book-manage/book-manage.component';
import { OperationRecordComponent } from './operation-record/operation-record.component';
import {MainComponent} from "./main.component";
import {MainRoutingModule} from "./main-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {ElModule} from "element-angular/release/element-angular.module";

@NgModule({
  declarations: [
    MainComponent,
    OrderListComponent,
    OrderCountComponent,
    UserManageComponent,
    MenuManageComponent,
    FactoryManageComponent,
    ChargebackComponent,
    DeliveryComponent,
    MaterialManageComponent,
    BookManageComponent,
    OperationRecordComponent],
  imports: [
    BrowserModule,
    CommonModule,
    ElModule,
    MainRoutingModule
  ],
})
export class MainModule {
  
}
