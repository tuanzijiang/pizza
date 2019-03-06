import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";
import {CardModule} from "primeng/card";
import {ButtonModule} from 'primeng/button';
import {CUSTOM_ERROR_MESSAGES, NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CUSTOM_ERRORS} from "../directives/custom-errors";
import {LoginRoutingModule} from "./login-routing.module";

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
    LoginRoutingModule
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
