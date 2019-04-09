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
  showPage: boolean;

  constructor(private systemManageService: SystemManageService) {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.tempMenu = null;
    this.showPage = false;
    this.pizzaType = [];

    this.getPizzaType();

    this.systemManageService.getMenuList().subscribe(
      (menuList: Menu[]) => {
        this.menuList = menuList;
        this.showPage = true;
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
      {label: '售卖中', value: '售卖中'},
      {label: '已下架', value: '已下架'},
    ];

  }

  getPizzaType() {
    this.systemManageService.getTagList().subscribe(
      (tagList: any[]) => {
        this.pizzaType.push({label: '请选择种类', value: null});
        for(let tag of tagList) {
          this.pizzaType.push({label: tag, value: tag});
        }
      }
    );
  }

  changeStatus(menu: Menu) {
    this.systemManageService.changeMenuState(menu.id).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          menu.state = this.showContraryState(menu);
        }
      }
    )
  }

  showContraryState(state) {
    if(state == '已下架')
      return '售卖中';
    else
      return '已下架';
  }

  contraryStatus(menu: Menu): string {
    if (menu.state == '已下架') return '上架';
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
