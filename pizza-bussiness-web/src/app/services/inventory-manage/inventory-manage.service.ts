import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddressService} from "../address/address.service";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";
import {Material} from "../../modules/inventory-manage/material";
import {BuyIngredient} from "../../modules/inventory-manage/buy-ingredient";

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
    return this.http.post(AddressService.editIngredientDetail(), material, httpOptions)
      .pipe()
  }

  addNewIngredient(material: Material):Observable<any> {
    return this.http.post(AddressService.addNewIngredient(), material, httpOptions)
      .pipe()
  }

  uploadIngredient(file: File):Observable<any> {
    let formData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(AddressService.uploadIngredients(), formData, fileOptions)
      .pipe()
  }

  getAlarmList():Observable<any> {
    return this.http.get(AddressService.getAlarmList(), httpOptions)
      .pipe()
  }

  buyIngredient(ingredient: BuyIngredient):Observable<any> {
    return this.http.post(AddressService.buyIngredient(), ingredient, httpOptions)
      .pipe()
  }

  removeIngredient(id: string):Observable<any> {
    return this.http.get(AddressService.removeIngredient() + id, httpOptions)
      .pipe()
  }
}
