import { NgModule} from '@angular/core';
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
import {TableModule} from 'primeng/table';
import {MultiSelectModule} from "primeng/multiselect";
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {ProgressBarModule} from 'primeng/progressbar';
import {
  CalendarModule,
  CardModule,
  DropdownModule,
  FileUploadModule,
  PanelModule,
  RadioButtonModule, TabViewModule
} from "primeng/primeng";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

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
    BrowserAnimationsModule,
    FormsModule,
    CommonModule,
    ElModule,
    MainRoutingModule,
    TableModule,
    MultiSelectModule,
    InputTextModule,
    ButtonModule,
    DialogModule,
    DropdownModule,
    CardModule,
    CalendarModule,
    FileUploadModule,
    RadioButtonModule,
    PanelModule,
    TabViewModule,
    ProgressBarModule
  ],
})
export class MainModule {
  
}
