import {Component, OnInit} from '@angular/core';
import {Material} from "../../../modules/inventory-manage/material";

@Component({
  selector: 'app-material-manage',
  templateUrl: './material-manage.component.html',
  styleUrls: ['./material-manage.component.scss']
})
export class MaterialManageComponent implements OnInit {

  cols: any[];
  materials: Material[];
  status: any[];
  dialogHeader: string;
  displayChangeDialog: boolean;
  displayAddDialog: boolean;
  displayImportDialog: boolean;
  tempMaterial: Material;
  selectedFile: File;
  showAlert: boolean;

  constructor() {
  }

  ngOnInit() {
    this.displayChangeDialog = false;
    this.displayAddDialog = false;
    this.displayImportDialog = true;
    this.showAlert = false;

    this.cols = [
      {field: 'material_id', header: '原料ID'},
      {field: 'name', header: '原料名称'},
      {field: 'inventory', header: '库存量'},
      {field: 'provider', header: '供应商'},
      {field: 'threshold', header: '安全阈值'},
      {field: 'status', header: '状态'},
    ];

    this.status = [
      {label: '请选择状态', value: null},
      {label: '使用中', value: '使用中'},
      {label: '停用中', value: '停用中'},
      ];

    this.materials = [
      {material_id: '1', name: '面粉', inventory: 3000, provider: '上海兰溪食品有限公司', threshold: 50, status: '使用中'},
      {material_id: '2', name: '香油', inventory: 1000, provider: '上海兰溪食品有限公司', threshold: 100, status: '停用中'},
      {material_id: '3', name: '胡椒', inventory: 900, provider: '上海兰溪食品有限公司', threshold: 200, status: '使用中'},
      {material_id: '4', name: '牛肉', inventory: 7000, provider: '上海兰溪食品有限公司', threshold: 300, status: '停用中'},
      {material_id: '5', name: '猪肉', inventory: 10000, provider: '上海兰溪食品有限公司', threshold: 1000, status: '使用中'},
    ]
  }

  onRowCancel(mat: Material) {
    if (this.materials.find(obj => obj.material_id == mat.material_id) != null) {
      this.materials = this.materials.filter(obj => obj.material_id != mat.material_id);
    }
  }

  addMaterial() {
    this.tempMaterial = new Material();
    this.dialogHeader = "新增原料";
    this.displayAddDialog = true;
  }

  editMaterial(mat: Material) {
    this.tempMaterial = mat;
    this.dialogHeader = "修改原料";
    this.displayChangeDialog = true;
  }


  submitChangedMat() {
    this.displayChangeDialog = false;
    this.tempMaterial = null;
  }

  submitAddMat() {
    this.displayChangeDialog = false;
    this.tempMaterial = null;
  }

  closeDialog() {
    this.displayChangeDialog = false;
    this.displayAddDialog = false;
    this.displayImportDialog = false;
    this.selectedFile = null;
    this.showAlert = false;
    this.tempMaterial = null;
  }

  importMaterial() {
    this.displayImportDialog = true;
  }

  submitImportMat() {
    if(this.selectedFile == null) {
      this.showAlert = true;
    } else {
      this.selectedFile = null;
      this.displayImportDialog = false;
    }
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      let reader = new FileReader();

      reader.readAsDataURL(event.target.files[0]); // read file as data url
      this.selectedFile = event.target.files[0];
      this.showAlert = false;
    }
  }

}
