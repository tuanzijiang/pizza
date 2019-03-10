import { Component, OnInit } from '@angular/core';
import {OperationRecord} from "../../modules/operation-record/operation-record";

@Component({
  selector: 'app-operation-record',
  templateUrl: './operation-record.component.html',
  styleUrls: ['./operation-record.component.scss']
})
export class OperationRecordComponent implements OnInit {

  cols: any[];
  operations: OperationRecord[];
  opType: any[];

  constructor() { }

  ngOnInit() {
    this.cols = [
      {field: 'manager_id', header: '管理员ID'},
      {field: 'type', header: '操作类别'},
      {field: 'object', header: '操作对象'},
      {field: 'time', header: '操作时间'},
    ];

    this.operations = [
      {manager_id: 'HJH123321', type: '新增', object: '披萨菜单', time: new Date('2019-03-10 18:00:00')},
      {manager_id: 'GHC148575', type: '删除', object: '披萨菜单', time: new Date('2019-03-08 18:00:00')},
      {manager_id: 'LOC573910', type: '新增', object: '披萨工厂', time: new Date('2019-03-01 18:00:00')},
      {manager_id: 'EJD654564', type: '修改', object: '披萨菜单', time: new Date('2019-02-10 18:00:00')},
    ];

    this.opType = [
      {label: '请选择类别', value: null},
      {label: '新增', value: '新增'},
      {label: '删除', value: '删除'},
      {label: '修改', value: '修改'},
    ];
  }


}
