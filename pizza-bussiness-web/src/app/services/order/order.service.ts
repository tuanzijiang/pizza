import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import { map } from 'rxjs/operators';
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
      .pipe(map((result: Response) => result.json()))
  }

  getYesterdayOrder(): Observable<any> {
    return this.http.get(AddressService.getYesterdayOrder(), httpOptions)
      .pipe(map((result: Response) => result.json()))
  }

  getOrderCountStatus(): Observable<any> {
    return this.http.get(AddressService.getOrderCountList(), httpOptions)
      .pipe(map((result: Response) => result.json()))
  }


}
