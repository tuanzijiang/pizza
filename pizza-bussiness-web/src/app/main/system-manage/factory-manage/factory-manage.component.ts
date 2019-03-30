import {Component, OnInit} from '@angular/core';
import {Ingredient} from "../../../modules/system-manage/ingredient";
import {Factory} from "../../../modules/system-manage/factory";

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

  constructor() {
  }

  ngOnInit() {
    this.displayAddDialog = false;
    this.displayChangeDialog = false;
    this.checkIgDialog = false;

    this.cols = [
      {field: 'fac_id', header: '编号'},
      {field: 'name', header: '工厂名称'},
      {field: 'pic', header: '缩略图'},
      {field: 'address', header: '工厂地址'},
      {field: 'area', header: '区域'},
      {field: 'open_hours', header: '营业时间'},
      {field: 'phone', header: '联系电话'},
      {field: 'max_orders', header: '最大接单量'},
    ];

    this.igList = [
      {ig_name: '刘昊然', ig_num: 100},
      {ig_name: '吴磊', ig_num: 300},
      {ig_name: '彭昱畅', ig_num: 1000},
      {ig_name: '迪丽热巴', ig_num: 1000},
    ];

    this.factories = [
      {
        fac_id: '1',
        name: '刘昊然的工厂',
        pic: '../../../../assets/images/example.jpeg',
        address: '上海白马会所',
        area: '长宁区',
        open_hours: '00:00-23:59',
        phone: '15284847463',
        max_orders: 100,
        longitude: '31.231784',
        latitude: '121.398232',
        ingridients: this.igList
      },
      {
        fac_id: '2',
        name: '吴磊的工厂',
        pic: '../../../../assets/images/example.jpeg',
        address: '上海白马会所',
        area: '普陀区',
        open_hours: '00:00-23:59',
        phone: '1524748384',
        max_orders: 300,
        longitude: '31.231784',
        latitude: '121.398232',
        ingridients: this.igList
      },
      {
        fac_id: '3',
        name: '白敬亭的工厂',
        pic: '../../../../assets/images/example.jpeg',
        address: '上海白马会所',
        area: '浦东新区',
        open_hours: '00:00-23:59',
        phone: '15284847463',
        max_orders: 100,
        longitude: '31.231784',
        latitude: '121.398232',
        ingridients: this.igList
      },
      {
        fac_id: '4',
        name: '魏大勋的工厂',
        pic: '../../../../assets/images/example.jpeg',
        address: '上海白马会所',
        area: '静安区',
        open_hours: '00:00-23:59',
        phone: '15229294748',
        max_orders: 1000,
        longitude: '31.231784',
        latitude: '121.398232',
        ingridients: this.igList
      },
      {
        fac_id: '5',
        name: '杨祐宁的工厂',
        pic: '../../../../assets/images/example.jpeg',
        address: '上海白马会所',
        area: '长宁区',
        open_hours: '00:00-23:59',
        phone: '15284847463',
        max_orders: 2000,
        longitude: '31.231784',
        latitude: '121.398232',
        ingridients: this.igList
      },
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
    this.tempFactory.open_hours = '00:00-23:59';
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
    this.imgUrl = this.tempFactory.pic;
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
    this.tempFactory.open_hours = this.dateToStr(this.startDate) + "-" + this.dateToStr(this.endDate);
    this.displayChangeDialog = false;
    this.tempFactory = null;
  }

  submitAddFac() {
    this.tempFactory.open_hours = this.dateToStr(this.startDate) + "-" + this.dateToStr(this.endDate);
    this.displayAddDialog = false;
    this.tempFactory = null;
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url
      this.tempFactory.pic = event.target.files[0];

      reader.onload = (event : any) => { // called once readAsDataURL is completed
        this.imgUrl = event.target.result;
      }
    }
  }

  strToDate() {
    let arr = this.tempFactory.open_hours.split('-');
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
  pad(num:number, size:number): string {
    let s = num+"";
    while (s.length < size) s = "0" + s;
    return s;
  }
}
