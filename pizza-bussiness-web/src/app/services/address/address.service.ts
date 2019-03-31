import {Injectable} from '@angular/core';
import {local} from "./domain";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor() {
  }

  static getLogin() {
    return `${local}/admin/login`
  }

  static getOrderList() {
    return `${local}/order/getOrderList`
  }

  static getYesterdayOrder() {
    return `${local}/order/getYesterdaySaleStatus`
  }

  static getOrderCountList() {
    return `${local}/order/getMonthSaleStatus`
  }

  static getUserList() {
    return `${local}/user/getUserList`
  }

  static getMenuList() {
    return `${local}/menu/getMenuList`
  }

  static editMenu() {
    return `${local}/menu/editMenuDetail`
  }

  static changeMenuState() {
    return `${local}/menu/editMenuStatus?menuId=`
  }

  static getShopList() {
    return `${local}/shop/getShopList`
  }

  static editShop() {
    return `${local}/shop/editShopDetail`
  }

  static getDriverList() {
    return `${local}/driver/getDriverList`
  }
}
