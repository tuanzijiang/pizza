import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddressService} from "../address/address.service";
import {map} from "rxjs/operators";
import {Menu} from "../../modules/system-manage/menu";
import {Factory} from "../../modules/system-manage/factory";
import {AuthService} from "../auth/auth.service";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class SystemManageService {

  constructor(private http: HttpClient) { }

  getUserList():Observable<any> {
    return this.http.get(AddressService.getUserList(), httpOptions)
      .pipe()
  }

  getTagList(): Observable<any> {
    return this.http.get(AddressService.getTagList(), httpOptions)
      .pipe()
  }

  getMenuList():Observable<any> {
    return this.http.get(AddressService.getMenuList(), httpOptions)
      .pipe()
  }

  editMenu(menu: Menu):Observable<any> {
    return this.http.post(AddressService.editMenu() + '?adminId=' + AuthService.UserId, menu, httpOptions)
      .pipe()
  }

  addMenu(menu: Menu):Observable<any> {
    return this.http.post(AddressService.addMenu() + '?adminId=' + AuthService.UserId, menu, httpOptions)
      .pipe()
  }

  changeMenuState(id: string):Observable<any> {
    return this.http.get(AddressService.changeMenuState() + id + '&adminId=' + AuthService.UserId, httpOptions)
      .pipe()
  }

  getShopList():Observable<any> {
    return this.http.get(AddressService.getShopList(), httpOptions)
      .pipe()
  }

  editShop(shop: Factory):Observable<any> {
    return this.http.post(AddressService.editShop() + '?adminId=' + AuthService.UserId, shop, httpOptions)
      .pipe()
  }

  addShop(shop: Factory):Observable<any> {
    return this.http.post(AddressService.addShop() + '?adminId=' + AuthService.UserId, shop, httpOptions)
      .pipe()
  }

}
