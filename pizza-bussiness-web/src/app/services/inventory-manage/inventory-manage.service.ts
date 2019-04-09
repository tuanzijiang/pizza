import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddressService} from "../address/address.service";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import {Material} from "../../modules/inventory-manage/material";
import {BuyIngredient} from "../../modules/inventory-manage/buy-ingredient";
import {AuthService} from "../auth/auth.service";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

const fileOptions = {
  headers: new HttpHeaders({
    'Accept': 'application/json'
  })
};
@Injectable({
  providedIn: 'root'
})
export class InventoryManageService {

  constructor(private http: HttpClient) { }

  getIngredientList():Observable<any> {
    return this.http.get(AddressService.getIngredientList(), httpOptions)
      .pipe()
  }

  editIngredientDetail(material: Material):Observable<any> {
    return this.http.post(AddressService.editIngredientDetail() + '?adminId=' + AuthService.UserId, material, httpOptions)
      .pipe()
  }

  addNewIngredient(material: Material):Observable<any> {
    return this.http.post(AddressService.addNewIngredient() + '?adminId=' + AuthService.UserId, material, httpOptions)
      .pipe()
  }

  uploadIngredient(file: File):Observable<any> {
    let formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(AddressService.uploadIngredients() + '?adminId=' + AuthService.UserId, formData, fileOptions)
      .pipe()
  }

  getAlarmList():Observable<any> {
    return this.http.get(AddressService.getAlarmList(), httpOptions)
      .pipe()
  }

  buyIngredient(ingredient: BuyIngredient):Observable<any> {
    return this.http.post(AddressService.buyIngredient() + '?adminId=' + AuthService.UserId, ingredient, httpOptions)
      .pipe()
  }

  removeIngredient(id: string):Observable<any> {
    return this.http.get(AddressService.removeIngredient() + id + '&adminId=' + AuthService.UserId, httpOptions)
      .pipe()
  }
}
