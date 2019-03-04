import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  userName: string;
  constructor(
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.userName = this.route.snapshot.paramMap.get('userName');
    console.log(this.userName);
  }

}
