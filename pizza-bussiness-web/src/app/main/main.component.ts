import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  userName: string;
  constructor(
    private router: Router
  ) { }

  ngOnInit() {
    this.userName = AuthService.UserName;
  }

  goToOrderList() {
  }
}
