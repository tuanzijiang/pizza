import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ErrorMessage} from "ng-bootstrap-form-validation";
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  formGroup: FormGroup;
  buttonDisabled: boolean;
  userName: string;
  password: string;

  customErrorMessages: ErrorMessage[] = [
    {
      error: 'required',
      format: (label, error) => `${label.toUpperCase()}不能为空！`
    }
  ];

  constructor(
    private router: Router,
    public authService: AuthService
  ) {
  }

  ngOnInit() {
    this.buttonDisabled = false;
    this.formGroup = new FormGroup({
      UserName: new FormControl('', [
        Validators.required,
      ]),
      Password: new FormControl('', [
        Validators.required,
      ])
    });
  }

  onSubmit() {
    this.buttonDisabled = true;
    this.authService.login(this.userName).subscribe(
      () => {
        let redirect = this.authService.redirectUrl ? this.authService.redirectUrl : '/order-list';
        // Set our navigation extras object
        // that passes on our global query params and fragment
        let navigationExtras: NavigationExtras = {
          queryParamsHandling: 'preserve',
          preserveFragment: true
        };

        // Redirect the user
        this.router.navigate([redirect], navigationExtras);
      }
    );}

  onReset() {
    this.formGroup.reset();
  }

}
