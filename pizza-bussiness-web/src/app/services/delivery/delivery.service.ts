import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddressService} from "../address/address.service";
import {Delivery} from "../../modules/delivery/delivery";
import {AuthService} from "../auth/auth.service";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  constructor(private http: HttpClient) { }

  getDriverList(): Observable<any> {
    return this.http.get(AddressService.getDriverList(), httpOptions)
      .pipe()
  }

  editDriver(driver: Delivery): Observable<any> {
    return this.http.post(AddressService.editDriver() + '?adminId=' + AuthService.UserId, driver, httpOptions)
      .pipe()
  }

  addDriver(driver: Delivery): Observable<any> {
    return this.http.post(AddressService.addDriver() + '?adminId=' + AuthService.UserId, driver, httpOptions)
      .pipe()
  }

  removeDriver(id: string): Observable<any> {
    return this.http.get(AddressService.removeDriver() + id + '&adminId=' + AuthService.UserId, httpOptions)
      .pipe()
  }

}
