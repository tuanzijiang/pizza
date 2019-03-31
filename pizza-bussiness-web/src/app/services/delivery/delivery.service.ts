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
export class DeliveryService {

  constructor(private http: HttpClient) { }

  getUserList():Observable<any> {
    return this.http.get(AddressService.getDriverList(), httpOptions)
      .pipe(map((result: Response) => result.json()))
  }

}
