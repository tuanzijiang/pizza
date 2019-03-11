import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from "./main.component";
import { OrderListComponent } from "./order/order-list/order-list.component";
import {ChargebackComponent} from "./chargeback/chargeback.component";
import {DeliveryComponent} from "./delivery/delivery.component";
import {BookManageComponent} from "./inventory-manage/book-manage/book-manage.component";
import {MaterialManageComponent} from "./inventory-manage/material-manage/material-manage.component";
import {OperationRecordComponent} from "./operation-record/operation-record.component";
import {OrderCountComponent} from "./order/order-count/order-count.component";
import {FactoryManageComponent} from "./system-manage/factory-manage/factory-manage.component";
import {MenuManageComponent} from "./system-manage/menu-manage/menu-manage.component";
import {UserManageComponent} from "./system-manage/user-manage/user-manage.component";
import {LoginComponent} from "../login/login.component";
import {AuthGuard} from "../services/auth/auth.guard";
const mainRoutes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: '/order-list', pathMatch: 'full'},
      { path: 'order-list', component: OrderListComponent },
      { path: 'order-count', component: OrderCountComponent },
      { path: 'chargeback', component: ChargebackComponent },
      { path: 'delivery', component: DeliveryComponent },
      { path: 'book-manage', component: BookManageComponent },
      { path: 'material-manage', component: MaterialManageComponent },
      { path: 'operation-record', component: OperationRecordComponent},
      { path: 'factory-manage', component: FactoryManageComponent },
      { path: 'menu-manage', component: MenuManageComponent },
      { path: 'user-manage', component: UserManageComponent },
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  }

];

@NgModule({
  imports: [
    RouterModule.forChild(mainRoutes),],
  exports: [RouterModule]
})
export class MainRoutingModule { }
