import {Component, OnInit} from '@angular/core';
import {Menu} from "../../../modules/system-manage/menu";
import {Ingredient} from "../../../modules/system-manage/ingredient";
import {SystemManageService} from "../../../services/system-manage/system-manage.service";
import {BaseResponse} from "../../../modules/baseResponse";

@Component({
  selector: 'app-menu-manage',
  templateUrl: './menu-manage.component.html',
  styleUrls: ['./menu-manage.component.scss']
})
export class MenuManageComponent implements OnInit {

  cols: any[];
  igcols: any[];
  menuList: Menu[];
  states: any[];
  pizzaType: any[];
  displayAddDialog: boolean;
  displayChangeDialog: boolean;
  tempMenu: Menu;
  imgUrl: string;

  constructor(private systemManageService: SystemManageService) {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.tempMenu = null;

    this.systemManageService.getMenuList().subscribe(
      (menuList: Menu[]) => {
        this.menuList = menuList;
      }
    );

    this.cols = [
      {field: 'id', header: '编号'},
      {field: 'name', header: '披萨名称'},
      {field: 'tagName', header: '披萨类别'},
      {field: 'image', header: '缩略图'},
      {field: 'price', header: '价格(元)'},
      {field: 'description', header: '商品简述'},
      {field: 'state', header: '状态'},
    ];

    this.igcols = [
      {field: 'name', header: '名称'},
      {field: 'count', header: '数量(可修改)'},
    ];

    this.states = [
      {label: '请选择状态', value: null},
      {label: '上架', value: '上架'},
      {label: '下架', value: '下架'},
    ];

    this.pizzaType = [
      {label: '请选择种类', value: null},
      {label: '大方薄底披萨', value: '大方薄底披萨'},
      {label: '手拍纯珍披萨', value: '手拍纯珍披萨'},
      {label: '皇冠芝心披萨', value: '皇冠芝心披萨'},
      {label: '芝香烤肠披萨', value: '芝香烤肠披萨'},
    ];

  }

  changeStatus(menu: Menu) {
    this.systemManageService.changeMenuState(menu.id).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          menu.state = this.contraryStatus(menu);
        }
      }
    )
  }

  contraryStatus(menu: Menu): string {
    if (menu.state == '下架') return '上架';
    else return '下架';
  }

  alterMenu(menu: Menu) {
    this.tempMenu = menu;
    this.imgUrl = this.tempMenu.image;
    this.displayChangeDialog = true;
  }

  addMenu() {
    this.tempMenu = new Menu();
    this.displayAddDialog = true;
  }

  submitNewMenu() {
    this.systemManageService.addMenu(this.tempMenu).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayAddDialog = false;
          this.tempMenu = null;
        }
      }
    );
  }

  submitChangedMenu() {
    this.systemManageService.editMenu(this.tempMenu).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayChangeDialog = false;
          this.tempMenu = null;
        }
      }
    );
  }

  closeNewMenuDialog() {
    this.tempMenu = null;
    this.displayAddDialog = false;
  }

  closeChangeDialog() {
    this.tempMenu = null;
    this.displayChangeDialog = false;
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url
      this.tempMenu.image = event.target.files[0];

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.imgUrl = event.target.result;
      }
    }
  }

}
