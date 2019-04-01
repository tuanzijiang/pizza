import {Component, OnInit} from '@angular/core';
import {Material} from "../../../modules/inventory-manage/material";
import {InventoryManageService} from "../../../services/inventory-manage/inventory-manage.service";
import {BaseResponse} from "../../../modules/baseResponse";

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

  constructor(private inventoryManageService: InventoryManageService) {
  }

  ngOnInit() {
    this.displayChangeDialog = false;
    this.displayAddDialog = false;
    this.displayImportDialog = false;
    this.showAlert = false;

    this.inventoryManageService.getIngredientList().subscribe(
      (materials: Material[]) => {
        this.materials = materials;
      }
    );

    this.cols = [
      {field: 'id', header: '原料ID'},
      {field: 'name', header: '原料名称'},
      {field: 'count', header: '库存量'},
      {field: 'supplierName', header: '供应商'},
      {field: 'alertNum', header: '安全阈值'},
      {field: 'ingredientStatus', header: '状态'},
    ];

    this.status = [
      {label: '请选择状态', value: null},
      {label: '使用中', value: '使用中'},
      {label: '停用中', value: '停用中'},
      ];
  }

  onRowCancel(mat: Material) {
    this.inventoryManageService.removeIngredient(mat.id).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          if (this.materials.find(obj => obj.id == mat.id) != null) {
            this.materials = this.materials.filter(obj => obj.id != mat.id);
          }
        }
      }
    );
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
    this.inventoryManageService.editIngredientDetail(this.tempMaterial).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayChangeDialog = false;
          this.tempMaterial = null;
        }
      }
    );
  }

  submitAddMat() {
    this.inventoryManageService.addNewIngredient(this.tempMaterial).subscribe(
      (response: BaseResponse) => {
        if (response.resultType == 'FAILURE') {
          alert(response.errorMsg);
        } else {
          this.displayChangeDialog = false;
          this.tempMaterial = null;
        }
      }
    );
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
      this.inventoryManageService.uploadIngredient(this.selectedFile).subscribe(
        (response: BaseResponse) => {
          if (response.resultType == 'FAILURE') {
            alert(response.errorMsg);
          } else {
            this.selectedFile = null;
            this.displayImportDialog = false;
          }
        }
      );
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
