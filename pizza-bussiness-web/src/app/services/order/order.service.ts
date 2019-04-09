import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
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

  getOrderDetail(id: string):Observable<any> {
    return this.http.get(AddressService.getOrderDetail() + id, httpOptions)
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

  getCancelOrderList(): Observable<any> {
    return this.http.get(AddressService.getCancelOrderList(), httpOptions)
      .pipe()
  }


}
