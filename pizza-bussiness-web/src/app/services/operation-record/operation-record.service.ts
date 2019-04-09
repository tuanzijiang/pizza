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
export class OperationRecordService {

  constructor(private http: HttpClient) { }

  getOperationLogger():Observable<any> {
    return this.http.get(AddressService.getOperationLogger(), httpOptions)
      .pipe()
  }
}
