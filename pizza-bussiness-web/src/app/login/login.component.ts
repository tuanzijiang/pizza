import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ErrorMessage} from "ng-bootstrap-form-validation";
import {ActivatedRoute, Router} from "@angular/router";

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
    private router: Router
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
    console.log(this.formGroup);
    this.router.navigate(['../main', this.userName])
  }

  onReset() {
    this.formGroup.reset();
  }

}
