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
  displayAddDialog: boolean;
  tempFactory: Factory;
  displayChangeDialog: boolean;
  checkIgDialog: boolean;
  showPage: boolean;
  imgUrl: string;
  header: string;
  startDate: Date;
  endDate: Date;
  ingredients: Ingredient[];
  image: File;

  constructor(private systemManageService: SystemManageService) {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.checkIgDialog = false;

    this.getShopList();

    this.cols = [
      {field: 'id', header: '编号'},
      {field: 'name', header: '工厂名称'},
      {field: 'image', header: '缩略图'},
      {field: 'address', header: '工厂地址'},
      {field: 'openHours', header: '营业时间'},
      {field: 'phone', header: '联系电话'},
      {field: 'maxNum', header: '最大接单量'},
    ];

  }

  getShopList() {
    this.systemManageService.getShopList().subscribe(
      (shopList: Factory[]) => {
        this.factories = shopList;
        this.showPage = true;
      }
    );
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
    this.systemManageService.getIngredientListByShopId(factory.id).subscribe(
      (ingredients: Ingredient[]) => {
        this.ingredients = ingredients;
        this.checkIgDialog = true;
      }
    );
  }

  alterFactory(factory: Factory) {
    this.header = "修改";
    this.tempFactory = this.cloneFactory(factory);
    this.imgUrl = this.tempFactory.image;
    this.strToDate();
    this.displayChangeDialog = true;
  }

  closeAddDialog() {
    this.displayAddDialog = false;
    this.tempFactory = null;
  }

  closeChangeDialog() {
    this.displayChangeDialog = false;
    this.tempFactory = null;
  }

  submitChangedFac() {
    this.tempFactory.startTime = this.dateToStr(this.startDate);
    this.tempFactory.endTime = this.dateToStr(this.endDate);
    this.systemManageService.editShop(this.tempFactory).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if(this.image == null) {
            this.displayChangeDialog = false;
            this.showPage = false;
            this.tempFactory = null;
            this.getShopList();
          } else {
            this.uploadImage(this.tempFactory.id);
          }
        }
      }
    );
  }

  submitAddFac() {
    this.tempFactory.startTime = this.dateToStr(this.startDate);
    this.tempFactory.endTime = this.dateToStr(this.endDate);
    this.systemManageService.addShop(this.tempFactory).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if(this.image == null) {
            this.displayAddDialog = false;
            this.showPage = false;
            this.tempFactory = null;
            this.getShopList();
          } else {
            this.uploadImage(response.shopId);
          }
        }
      }
    );
  }

  uploadImage(shopId: string) {
    this.systemManageService.uploadShopImage(this.image, shopId).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayAddDialog = false;
          this.displayChangeDialog = false;
          this.tempFactory = null;
          this.image = null;
          this.showPage = false;
          this.getShopList();
        }
      }
    );
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url
      this.image = event.target.files[0];

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
    return '2019-01-01 ' + hour + ":" + min + ':00'
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

  cloneFactory(fac: Factory) {
    let newFac = new Factory();
    for(const key in fac) {
      if(fac.hasOwnProperty(key)) {
        newFac[key] = fac[key];
      }
    }
    return newFac;
  }
}
