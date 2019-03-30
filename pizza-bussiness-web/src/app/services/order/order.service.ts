import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {OrderDetail} from "../../modules/order/orderDetail";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddressService} from "../address/address.service";

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
      .map((result: Response) => result.json())
  }


}
