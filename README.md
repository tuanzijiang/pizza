# pizza

软件开发实践课程项目：披萨订购系统。

---
### 项目结构说明
##### 后台系统代码
- `pizza-bussiness-server`：后台系统的服务端
- `pizza-bussiness-web`：后台系统的前端

##### 前台系统代码
- `pizza-consumer-server`：前台系统的服务端
- `pizza-consumer-web`： 前台系统的前端

##### 其他
- `pizza-data`：数据库访问模块
- `pizza-protobuf`：接口定义模块
- `unit-test-report`：单元测试报告
- `pizza-web-performance`：压力测试小工具
- `pizza-ui`：UI图

### 开发规范 
#### 一、 git commit message规范 
##### 格式模版
```
<类别>: <主题>
\n
<内容>
\n
<页脚>
```
##### 模版说明
1. 格式整体说明：
    - 整体由四部分组成：`类别`，`主题`，`内容`和`页脚`；
    - `类别`和`主题`为 __必填且放在同行，用`:`隔开__；
    - `内容`和`页脚`为 __选填，前后皆有一行的空行__；
e
2. 组成含义说明说明：
    - `类别`表示本次提交的种类，详见`3.`；
    - `主题`为本次提交的概述；
    - `内容`为主题的补充信息；
    - `页脚`为一些与此次提交没有直接关联的信息；
3. `类别`有一下个选项：
    - feat: 新的功能添加
    - fix: 修改bug
    - refactor: 对已有功能进行重构
    - docs: 修改文档信息
    - style： 格式（不影响代码运行的变动）
    - test：增加测试
    - chore：构建过程或辅助工具的变动

##### 实例
```
feat: 添加了订单页面

1. 添加了订单页面的相关组建
2. mock服务器上添加了订单列表的相关信息

```

#### 二、代码合并规范
##### 整体说明
master分支已经进行了保护，不能进行 __强推操作__，只能通过```pull requests```进行合并，__所有的功能开发在自己的feature分支上进行开发__;

##### 分支说明
1. master分支为开发的主分支，所有开发完成的代码要merge到该分支，该分支为不稳定的分支；
2. build/xxx分支为为后续准备的发版分支，此分支为稳定分支。其中xxx为版本号。为将来发到服务器上进行准备
3. {type}/xxx 分支为开发分支，其中`type`为message规范中的类别。(包括: feat,fix,refactor,docs,style,test,chore)，其中xxx为自定义的说明部分。

##### 重要说明
1. 在github上面进行```pull request```以后，后续不用的分支记得进行删除
2. 外部依赖的库文件夹，不要推到远程仓库，把这些文件夹添加到根目录的```.gitignore```中

#### 三、分组开发说明

1. A组：负责用户端的相关需求。(即：老师说的前台)
  - 前端代码`code/pizza-consumer-web`
  - 后端代码`code/pizza-consumer-server`
2. B组：负责商家端的相关需求。(即：老师说的后台)
  - 前端代码`code/pizza-business-web`
  - 后端代码`code/pizza-business-server`
---
