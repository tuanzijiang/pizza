import { Component, OnInit } from '@angular/core';
import {OperationRecord} from "../../modules/operation-record/operation-record";
import {OperationRecordService} from "../../services/operation-record/operation-record.service";

@Component({
  selector: 'app-operation-record',
  templateUrl: './operation-record.component.html',
  styleUrls: ['./operation-record.component.scss']
})
export class OperationRecordComponent implements OnInit {

  cols: any[];
  operations: OperationRecord[];
  opType: any[];
  showPage: boolean;

  constructor(private operationRecordService: OperationRecordService) { }

  ngOnInit() {
    this.showPage = false;
    this.operationRecordService.getOperationLogger().subscribe(
      (operations: OperationRecord[]) => {
        this.operations = operations;
        this.showPage = true;
      }
    );

    this.cols = [
      {field: 'adminId', header: '管理员ID'},
      {field: 'operateType', header: '操作类别'},
      {field: 'operateDetail', header: '操作对象'},
      {field: 'operateTime', header: '操作时间'},
    ];

    this.opType = [
      {label: '请选择类别', value: null},
      {label: '增加', value: '增加'},
      {label: '删除', value: '删除'},
      {label: '修改', value: '修改'},
    ];
  }


}
