import { Injectable } from '@angular/core';
import {local} from "./domain";
@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor() { }

  static getLogin() {
    return `${local}/admin/login`
  }

  static getOrderList() {
    return `${local}/order/getOrderList`
  }


}
