import {Injectable} from '@angular/core';
import {server} from "./domain";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor() {
  }

  static getLogin() {
    return `${server}/admin/login`
  }

  static getOrderList() {
    return `${server}/order/getOrderList`
  }

  static getOrderDetail() {
    return `${server}/order/getOrderDetail?orderId=`
  }

  static getYesterdayOrder() {
    return `${server}/order/getYesterdaySaleStatus`
  }

  static getOrderCountList() {
    return `${server}/order/getMonthSaleStatus`
  }

  static getUserList() {
    return `${server}/user/getUserList`
  }

  static getTagList() {
    return `${server}/menu/getTagList`
  }

  static getMenuList() {
    return `${server}/menu/getMenuList`
  }

  static editMenu() {
    return `${server}/menu/editMenuDetail`
  }

  static addMenu() {
    return `${server}/menu/addNewMenu`
  }

  static uploadMenuImage() {
    return `${server}/menu/uploadImage?menuId=`
  }

  static changeMenuState() {
    return `${server}/menu/editMenuStatus?menuId=`
  }

  static getMenuIngredients() {
    return `${server}/menu/getMenuIngredients?menuId=`
  }

  static getShopList() {
    return `${server}/shop/getShopList`
  }

  static editShop() {
    return `${server}/shop/editShopDetail`
  }

  static uploadShopImage() {
    return `${server}/shop/uploadImage?shopId=`
  }

  static addShop() {
    return `${server}/shop/addNewShop`
  }

  static getIngredientListByShopId() {
    return `${server}/shop/getIngredientListByShopId?shopId=`
  }

  static getDriverList() {
    return `${server}/driver/getDriverList`
  }

  static editDriver() {
    return `${server}/driver/editDriverDetail`
  }

  static addDriver() {
    return `${server}/driver/addNewDriver`
  }

  static removeDriver() {
    return `${server}/driver/removeDriver?id=`
  }

  static getIngredientList() {
    return `${server}/ingredient/getIngredientList`
  }

  static editIngredientDetail() {
    return `${server}/ingredient/editIngredientDetail`
  }

  static addNewIngredient() {
    return `${server}/ingredient/addNewIngredient`
  }

  static uploadIngredients() {
    return `${server}/ingredient/batchImportByExcelFile`
  }

  static getAlarmList() {
    return `${server}/ingredient/getAlarmList`
  }

  static buyIngredient() {
    return `${server}/ingredient/buyIngredient`
  }

  static removeIngredient() {
    return `${server}/ingredient/deleteIngredient?id=`
  }

  static allowOrder() {
    return `${server}/order/allowCancel?orderId=`
  }

  static getCancelOrderList() {
    return `${server}/order/getCancelOrderList`
  }

  static denyOrder() {
    return `${server}/order/denyCancel?orderId=`
  }

  static getOperationLogger() {
    return `${server}/admin/getOperateLogger`
  }

  static getPendingList() {
    return `${server}/order/getPendingRequestList`
  }

}
