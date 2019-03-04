import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {CardModule} from "primeng/card";
import {ButtonModule} from 'primeng/button';
import {CUSTOM_ERROR_MESSAGES, NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CUSTOM_ERRORS} from "../directives/custom-errors";

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports:[
    CardModule,
    ButtonModule,
    NgBootstrapFormValidationModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports:[CardModule, ButtonModule],
  providers: [{
    provide: CUSTOM_ERROR_MESSAGES,
    useValue: CUSTOM_ERRORS,
    multi: true
  }],
})

export class LoginModule {
}
