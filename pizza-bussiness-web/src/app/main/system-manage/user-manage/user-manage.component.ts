import {Component, OnInit} from '@angular/core';
import {UserManage} from '../../../modules/system-manage/userManage';

@Component({
  selector: 'app-user-manage',
  templateUrl: './user-manage.component.html',
  styleUrls: ['./user-manage.component.scss']
})
export class UserManageComponent implements OnInit {

  userList: UserManage[];
  cols: any[];

  constructor() {
  }

  ngOnInit() {
    this.userList = [
      {
        user_pic: '',
        nickname: 'Daniel',
        email: '1234567889@qq.com',
        other_account: 'fsdkhfh',
        address: '上海市普陀区中山北路3663号华东师范大学',
        distinct: '普陀区',
        birthday: new Date('1997-10-01'),
        latest_login: new Date('2019-03-07 17:59:37')
      },
      {
        user_pic: '',
        nickname: 'Jack',
        email: '316742364@qq.com',
        other_account: 'dsghsdfjkhg',
        address: '上海市普陀区中山北路3663号华东师范大学',
        distinct: '普陀区',
        birthday: new Date('1997-10-01'),
        latest_login: new Date('2019-03-06 17:59:37')
      },
      {
        user_pic: '',
        nickname: 'Jonathan',
        email: '432353453@qq.com',
        other_account: 'gndfskj',
        address: '上海市普陀区中山北路3663号华东师范大学',
        distinct: '普陀区',
        birthday: new Date('1997-10-01'),
        latest_login: new Date('2019-03-05 17:59:37')
      },
      {
        user_pic: '',
        nickname: 'Macy',
        email: '42342768679@qq.com',
        other_account: 'gdfsnkj',
        address: '上海市普陀区中山北路3663号华东师范大学',
        distinct: '普陀区',
        birthday: new Date('1997-10-01'),
        latest_login: new Date('2019-03-04 17:59:37')
      },
      {
        user_pic: '',
        nickname: 'Emma',
        email: '423423099@qq.com',
        other_account: 'gfdsnkj',
        address: '上海市普陀区中山北路3663号华东师范大学',
        distinct: '普陀区',
        birthday: new Date('1997-10-01'),
        latest_login: new Date('2019-03-03 17:59:37')
      },
    ];

    this.cols = [
      {field: 'user_pic', header: '用户头像'},
      {field: 'nickname', header: '昵称'},
      {field: 'email', header: '邮箱'},
      {field: 'phone', header: '手机号'},
      {field: 'other_account', header: '第三方账号'},
      {field: 'address', header: '默认地址'},
      {field: 'distinct', header: '常住区域'},
      {field: 'birthday', header: '生日'},
      {field: 'latest_login', header: '最近登录时间'},
    ];
  }

}
