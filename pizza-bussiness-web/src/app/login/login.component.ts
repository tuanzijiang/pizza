import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ErrorMessage} from "ng-bootstrap-form-validation";
import { NavigationExtras, Router} from "@angular/router";
import {AuthService} from "../services/auth/auth.service";
import {Admin} from "../modules/admin";
import {BaseResponse} from "../modules/baseResponse";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  formGroup: FormGroup;
  userName: string;
  password: string;
  admin: Admin;
  customErrorMessages: ErrorMessage[] = [
    {
      error: 'required',
      format: (label, error) => `${label.toUpperCase()}不能为空！`
    },
    {
      error: 'minlength',
      format: (label, error) => `${label.toUpperCase()}长度至少为6位！`
    },
    {
      error: 'maxlength',
      format: (label, error) => `${label.toUpperCase()}长度最多为12位！`
    },
    {
      error: 'pattern',
      format: (label, error) => `${label.toUpperCase()}不能包含空格！`
    }
  ];

  constructor(
    private router: Router,
    public authService: AuthService
  ) {
  }

  ngOnInit() {
    this.admin = new Admin();
    this.formGroup = new FormGroup({
      UserName: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[^\s]*$/)
      ]),
      Password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(12),
        Validators.pattern(/^[^\s]*$/)
      ])
    });
  }

  onSubmit() {
    this.admin.adminName = this.userName;
    this.admin.password = this.password;
    this.authService.login(this.admin).subscribe(
      (response: BaseResponse) => {
        if(response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          AuthService.UserId = response.adminId;
          let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/order-list';
          // Set our navigation extras object
          // that passes on our global query params and fragment
          let navigationExtras: NavigationExtras = {
            queryParamsHandling: '',
            preserveFragment: true
          };

          // Redirect the user
          this.router.navigate([redirect], navigationExtras);
        }
      },
    );
  }

  onReset() {
    this.formGroup.reset();
  }

}
