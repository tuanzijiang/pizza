import {Injectable} from '@angular/core';
import {delay, tap} from "rxjs/operators";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  static UserName: string;
  isLoggedIn = false;
  redirectUrl: string;

  constructor() {
  }

  login(userName): Observable<boolean> {
    return of(true).pipe(
      tap(val => {
        AuthService.UserName = userName;
        this.isLoggedIn = true})
    );
  }

  logout(): void {
    this.isLoggedIn = false;
  }
}
