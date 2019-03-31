import {Component, OnInit} from '@angular/core';
import {Ingredient} from "../../../modules/system-manage/ingredient";
import {Factory} from "../../../modules/system-manage/factory";
import {SystemManageService} from "../../../services/system-manage/system-manage.service";
import {BaseResponse} from "../../../modules/baseResponse";

@Component({
  selector: 'app-factory-manage',
  templateUrl: './factory-manage.component.html',
  styleUrls: ['./factory-manage.component.scss']
})
export class FactoryManageComponent implements OnInit {

  cols: any[];
  factories: Factory[];
  igList: Ingredient[];
  displayAddDialog: boolean;
  tempFactory: Factory;
  displayChangeDialog: boolean;
  checkIgDialog: boolean;
  imgUrl: string;
  areas: any[];
  header: string;
  startDate: Date;
  endDate: Date;

  constructor(private systemManageService: SystemManageService) {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.checkIgDialog = false;
    this.systemManageService.getShopList().subscribe(
      (shopList: Factory[]) => {
        this.factories = shopList;
      }
    );

    this.cols = [
      {field: 'id', header: '编号'},
      {field: 'name', header: '工厂名称'},
      {field: 'image', header: '缩略图'},
      {field: 'address', header: '工厂地址'},
      {field: 'area', header: '区域'},
      {field: 'openHours', header: '营业时间'},
      {field: 'phone', header: '联系电话'},
      {field: 'maxNum', header: '最大接单量'},
    ];

    this.initAreas();
  }

  initAreas() {
    this.areas = [];
    this.areas.push({label: '请选择种类', value: null});
    for (let factory of this.factories) {
      this.areas.push({label: factory.area, value: factory.area});
    }
  }

  addFactory() {
    this.startDate = new Date();
    this.endDate = new Date();
    this.header = "新增工厂";
    this.tempFactory = new Factory();
    this.tempFactory.openHours = '00:00-23:59';
    this.strToDate();
    this.displayAddDialog = true;
  }

  checkIg(factory: Factory) {
    this.tempFactory = factory;
    this.checkIgDialog = true;
  }

  alterFactory(factory: Factory) {
    this.header = "修改";
    this.tempFactory = factory;
    this.imgUrl = this.tempFactory.image;
    this.strToDate();
    this.displayChangeDialog = true;
  }

  closeDialog() {
    this.checkIgDialog = false;
    this.displayChangeDialog = false;
    this.displayAddDialog = false;
    this.tempFactory = null;
  }

  submitChangedFac() {
    this.tempFactory.openHours = this.dateToStr(this.startDate) + "-" + this.dateToStr(this.endDate);
    this.systemManageService.editShop(this.tempFactory).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayChangeDialog = false;
          this.tempFactory = null;
        }
      }
    );
  }

  submitAddFac() {
    this.tempFactory.openHours = this.dateToStr(this.startDate) + "-" + this.dateToStr(this.endDate);
    this.systemManageService.editShop(this.tempFactory).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayAddDialog = false;
          this.tempFactory = null;
        }
      }
    );
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url
      this.tempFactory.image = event.target.files[0];

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.imgUrl = event.target.result;
      }
    }
  }

  strToDate() {
    let arr = this.tempFactory.openHours.split('-');
    this.startDate = new Date();
    let arrSplit = arr[0].split(':');
    this.startDate.setHours(Number(arrSplit[0]));
    this.startDate.setMinutes(Number(arrSplit[1]));
    this.endDate = new Date();
    arrSplit = arr[1].split(':');
    this.endDate.setHours(Number(arrSplit[0]));
    this.endDate.setMinutes(Number(arrSplit[1]));
  }

  dateToStr(date: Date) {
    let hour: string = this.pad(date.getHours(), 2);
    let min: string = this.pad(date.getMinutes(), 2);
    return hour + ":" + min
  }

  /**
   * Convert num to string and add zeros
   * @param num
   * @param size
   */
  pad(num: number, size: number): string {
    let s = num + "";
    while (s.length < size) s = "0" + s;
    return s;
  }
}
