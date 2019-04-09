import {Component, OnInit, ViewChild} from '@angular/core';
import {Menu} from "../../../modules/system-manage/menu";
import {Ingredient} from "../../../modules/system-manage/ingredient";
import {SystemManageService} from "../../../services/system-manage/system-manage.service";
import {BaseResponse} from "../../../modules/baseResponse";
import {Table} from "primeng/table";

@Component({
  selector: 'app-menu-manage',
  templateUrl: './menu-manage.component.html',
  styleUrls: ['./menu-manage.component.scss']
})
export class MenuManageComponent implements OnInit {

  @ViewChild('dt') table: Table;
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
    this.getMenuList();

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

  getMenuList() {
    this.systemManageService.getMenuList().subscribe(
      (menuList: Menu[]) => {
        this.menuList = menuList;
        this.showPage = true;
      }
    );
  }

  getPizzaType() {
    this.systemManageService.getTagList().subscribe(
      (tagList: any[]) => {
        this.pizzaType.push({label: '请选择种类', value: null});
        for (let tag of tagList) {
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
          let index = this.menuList.findIndex(obj => obj.id == menu.id);
          this.menuList[index]['state'] = this.showContraryState(menu);
          this.table.reset();
          this.showPage = false;
          this.getMenuList();
        }
      }
    )
  }

  showContraryState(state) {
    if (state == '已下架')
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
    let newMenu = this.tempMenu;
    if (this.tempMenu.state == '售卖中') {
      newMenu.state = 'IN_SALE'
    } else {
      newMenu.state = 'OFF_SHELF'
    }
    this.systemManageService.addMenu(this.tempMenu).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayAddDialog = false;
          this.tempMenu = null;
          this.showPage = false;
          this.getMenuList();
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
          this.showPage = false;
          this.getMenuList();
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
