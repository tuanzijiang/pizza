import { Component } from '@angular/core';
import {Order} from "./modules/order/order";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  static orders: Order[];
  title = 'My First Angular App!';
}
