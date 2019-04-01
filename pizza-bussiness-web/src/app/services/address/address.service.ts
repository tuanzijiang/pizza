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

  static addMenu() {
    return `${local}/menu/addNewMenu`
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

  static addShop() {
    return `${local}/shop/addNewShop`
  }

  static getDriverList() {
    return `${local}/driver/getDriverList`
  }

  static editDriver() {
    return `${local}/driver/editDriverDetail`
  }

  static addDriver() {
    return `${local}/driver/addNewDriver`
  }

  static getIngredientList() {
    return `${local}/ingredient/getIngredientList`
  }

  static editIngredientDetail() {
    return `${local}/ingredient/editIngredientDetail`
  }

  static addNewIngredient() {
    return `${local}/ingredient/addNewIngredient`
  }

  static uploadIngredients() {
    return `${local}/ingredient/batchImportByExcelFile`
  }

  static getAlarmList() {
    return `${local}/ingredient/getAlarmList`
  }

  static buyIngredient() {
    return `${local}/ingredient/buyIngredient`
  }

  static removeIngredient() {
    return `${local}/ingredient/removeIngredient?id=`
  }

  static cancelBuyIngredient() {
    return `${local}/ingredient/cancelBuyIngredient?id=`
  }

  static allowOrder() {
    return `${local}/order/allowCancel?orderId=`
  }

  static denyOrder() {
    return `${local}/order/denyCancel?orderId=`
  }

  static getOperationLogger() {
    return `${local}/admin/getOperateLogger`
  }

  static getPendingList() {
    return `${local}/order/getPendingRequestList`
  }

}
