import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddressService} from "../address/address.service";
import {map} from "rxjs/operators";
import {Menu} from "../../modules/system-manage/menu";
import {Factory} from "../../modules/system-manage/factory";

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
    return this.http.post(AddressService.editMenu(), menu, httpOptions)
      .pipe()
  }

  addMenu(menu: Menu):Observable<any> {
    return this.http.post(AddressService.addMenu(), menu, httpOptions)
      .pipe()
  }

  changeMenuState(id: string):Observable<any> {
    return this.http.get(AddressService.changeMenuState() + id, httpOptions)
      .pipe()
  }

  getShopList():Observable<any> {
    return this.http.get(AddressService.getShopList(), httpOptions)
      .pipe()
  }

  editShop(shop: Factory):Observable<any> {
    return this.http.post(AddressService.editShop(), shop, httpOptions)
      .pipe()
  }

  addShop(shop: Factory):Observable<any> {
    return this.http.post(AddressService.addShop(), shop, httpOptions)
      .pipe()
  }

}
