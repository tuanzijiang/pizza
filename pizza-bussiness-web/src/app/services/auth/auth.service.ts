import {Injectable} from '@angular/core';
import {tap} from "rxjs/operators";
import {Observable,} from "rxjs";
import {Admin} from "../../modules/admin";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AddressService} from "../address/address.service";
import {BaseResponse} from "../../modules/baseResponse";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static UserName: string;
  static UserId: string;
  isLoggedIn = false;
  redirectUrl: string;

  constructor(private http: HttpClient) {
  }

  login(admin: Admin): Observable<BaseResponse> {
    return this.http.post<BaseResponse>(AddressService.getLogin(), admin, httpOptions)
      .pipe(
      tap(() => {
        AuthService.UserName = admin.adminName;
        this.isLoggedIn = true}),
      )
  }

  logout(): void {
    this.isLoggedIn = false;
  }

}
