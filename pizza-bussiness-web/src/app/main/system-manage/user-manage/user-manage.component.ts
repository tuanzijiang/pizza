import {Component, OnInit} from '@angular/core';
import {User} from '../../../modules/system-manage/user';
import {SystemManageService} from "../../../services/system-manage/system-manage.service";

@Component({
  selector: 'app-user-manage',
  templateUrl: './user-manage.component.html',
  styleUrls: ['./user-manage.component.scss']
})
export class UserManageComponent implements OnInit {

  userList: User[];
  cols: any[];
  showPage: boolean;

  constructor(private systemManageService: SystemManageService) {
  }

  ngOnInit() {
    this.showPage = false;
    this.systemManageService.getUserList().subscribe(
      (userList: User[]) => {
        this.userList = [];
        for(let user of userList) {
          let newUser = new User();
          newUser.image = user.image;
          newUser.name = user.name;
          newUser.email = user.email;
          newUser.phone = user.phone;
          newUser.weChat = user.weChat;
          newUser.defaultUserAddress = user.defaultUserAddress;
          newUser.city = user.city;
          newUser.birthday = new Date(user.birthday);
          newUser.latestLoginTime = new Date(user.latestLoginTime);
          this.userList.push(newUser);
        }
        this.showPage = true;
      }
    );

    this.cols = [
      {field: 'image', header: '用户头像'},
      {field: 'name', header: '昵称'},
      {field: 'email', header: '邮箱'},
      {field: 'phone', header: '手机号'},
      {field: 'weChat', header: '第三方账号'},
      {field: 'defaultUserAddress', header: '默认地址'},
      {field: 'city', header: '常住区域'},
      {field: 'birthday', header: '生日'},
      {field: 'latestLoginTime', header: '最近登录时间'},
    ];
  }

}
