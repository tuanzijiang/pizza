import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddressService} from "../address/address.service";
import {map} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})
export class ChargebackService {

  constructor(private http: HttpClient) { }

  getPendingList(): Observable<any> {
    return this.http.get(AddressService.getPendingList(), httpOptions)
      .pipe()
  }

  allowOrder(id: string): Observable<any> {
    return this.http.get(AddressService.allowOrder() + id, httpOptions)
      .pipe()
  }

  denyOrder(id: string): Observable<any> {
    return this.http.get(AddressService.denyOrder() + id, httpOptions)
      .pipe()
  }
}
