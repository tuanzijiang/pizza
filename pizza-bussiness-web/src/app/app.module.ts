import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginModule} from "./login/login.module";
import { setTheme } from 'ngx-bootstrap/utils';
import {NgBootstrapFormValidationModule} from "ng-bootstrap-form-validation";
import { ElModule } from 'element-angular'
import {MainModule} from "./main/main.module";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    LoginModule,
    MainModule,
    ElModule.forRoot(),
    NgBootstrapFormValidationModule.forRoot(),
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],})
export class AppModule {
  constructor() {
    setTheme("bs4");
  }
}
