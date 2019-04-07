import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import { map } from 'rxjs/operators';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddressService} from "../address/address.service";
import {OrderCountDate} from "../../modules/order/orderCountDate";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  getOrders():Observable<any> {
    return this.http.get(AddressService.getOrderList(), httpOptions)
      .pipe()
  }

  getYesterdayOrder(): Observable<any> {
    return this.http.get(AddressService.getYesterdayOrder(), httpOptions)
      .pipe()
  }

  getOrderCountStatus(orderCountDate: OrderCountDate): Observable<any> {
    return this.http.post(AddressService.getOrderCountList(), orderCountDate, httpOptions)
      .pipe()
  }


}
