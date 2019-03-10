import {Component, OnInit} from '@angular/core';
import {Menu} from "../../../modules/system-manage/menu";
import {Ingredent} from "../../../modules/system-manage/ingredent";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ErrorMessage} from "ng-bootstrap-form-validation";

@Component({
  selector: 'app-menu-manage',
  templateUrl: './menu-manage.component.html',
  styleUrls: ['./menu-manage.component.scss']
})
export class MenuManageComponent implements OnInit {

  cols: any[];
  igcols: any[];
  menuList: Menu[];
  igList: Ingredent[];
  states: any[];
  pizzaType: any[];
  displayAddDialog: boolean;
  displayChangeDialog: boolean;
  tempMenu: Menu;
  uploadedImages: any[];
  imgUrl: string;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.tempMenu = null;
    this.uploadedImages = [];

    this.cols = [
      {field: 'pizza_id', header: '编号'},
      {field: 'name', header: '披萨名称'},
      {field: 'type', header: '披萨类别'},
      {field: 'pic', header: '缩略图'},
      {field: 'price', header: '价格(元)'},
      {field: 'desc', header: '商品简述'},
      {field: 'status', header: '状态'},
    ];

    this.igcols = [
      {field: 'name', header: '名称'},
      {field: '数量', header: '数量(可修改)'},
    ];

    this.igList = [
      {ig_name: '刘昊然', ig_num: 100},
      {ig_name: '吴磊', ig_num: 300},
      {ig_name: '彭昱畅', ig_num: 1000},
      {ig_name: '迪丽热巴', ig_num: 1000},
    ];

    this.menuList = [
      {
        pizza_id: '1',
        name: '煎饼',
        type: '大方薄底披萨',
        pic: '../../../../assets/images/example.jpeg',
        price: 38.5,
        desc: 'This is a new food',
        status: '上架',
        ingridients: this.igList
      },
      {
        pizza_id: '2',
        name: '冒菜',
        type: '大方薄底披萨',
        pic: '../../../../assets/images/example.jpeg',
        price: 38.5,
        desc: 'This is a new food',
        status: '下架',
        ingridients: this.igList
      },
      {
        pizza_id: '3',
        name: '火锅',
        type: '大方薄底披萨',
        pic: '../../../../assets/images/example.jpeg',
        price: 38.5,
        desc: 'This is a new food',
        status: '上架',
        ingridients: this.igList
      },
      {
        pizza_id: '4',
        name: '麻辣烫',
        type: '大方薄底披萨',
        pic: '../../../../assets/images/example.jpeg',
        price: 38.5,
        desc: 'This is a new food',
        status: '下架',
        ingridients: this.igList
      },

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
    alert("status changed！！！！！！！！！！！！");
  }

  contraryStatus(menu: Menu): string {
    if (menu.status == '下架') return '上架';
    else return '下架';
  }

  alterMenu(menu: Menu) {
    this.tempMenu = menu;
    this.imgUrl = this.tempMenu.pic;
    this.displayChangeDialog = true;
  }

  addMenu() {
    this.tempMenu = new Menu();
    this.displayAddDialog = true;
  }

  submitNewMenu() {
    this.displayChangeDialog = false;
    this.tempMenu = null;
  }

  submitChangedMenu() {
    this.displayChangeDialog = false;
    this.tempMenu = null;
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
      this.tempMenu.pic = event.target.files[0];

      reader.onload = (event) => { // called once readAsDataURL is completed
        this.imgUrl = event.target.result;
      }
    }
  }

}
