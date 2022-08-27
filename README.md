# 水泥管道生产管理系统项目设计

## 项目总需求

1. 完成用户的注册和登录
2. 添加原材料的信息
3. 修改原材料的信息
4. 添加水泥管道的库存信息
5. 修改原材料的信息
6. 记录售出信息
7. 完成Excel表导出

## 需求分解

### 权限

- 管理员
- 用户

### 登录

所有者权限

### 注册

管理员权限

### 忘记密码

管理员权限

### 原材料的入库数量与出库数量

所有权限

### 产品的入库数量与出库数量

所有权限

### 产品出库单

管理员权限

### 原材料与产品的月报表

管理员权限



### 详细内容

#### 原材料

1.原材料入库

- 原材料名称，材料型号，入库数量，单位，材料价格(每吨、方/元)

2.原材料入库记录

-  入库记录显示编号，原材料名称，材料型号，入库数量，单位，材料价格(元)，入库时间 
- 按时间记录排序

3.原材料出库

- 原材料名称，材料型号，出库数量，单位

4.原材料出库记录

- 出库记录显示 ，编号，原材料名称，材料型号，出库数量，库存数量，单位，出库时间
- 以累加的方式展示

​	

#### 产品

1.产品入库

1.入库 基本信息包括 数量单位以前  时间

2. 修改全修改入库信息。
3. 出库，日期，价格，型号，数量，买方（生成出库单，并下载）（管理员）
4. 在出库后，完成库存数量的更新，显示单位以前。



#### 数据

##### 月报表

原材料的名称，型号，入库总数，出库总数，余量，年月

产品的名称，型号，入库总数，出库总数，余量，当月售出总金额，年月



### 普通用户

#### 原材料

钢筋吨

1.原材料入库

2.原材料出库

3.原材料查看库存

入库显示价格和日期，数量

入库按时间记录排序





出库

显示 	使用数量，剩余数量，时间 以累加的方式展示	

#### 产品

1.入库 基本信息包括 数量单位以前  时间

2. 修改全修改入库信息。
3. 出库，日期，价格，型号，数量，买方（生成出库单，并下载）（管理员）
4. 在出库后，完成库存数量的更新，显示单位以前。





## 数据库设计



```
drop database if exists `cement_manage`;
create database `cement_manage` character set utf8;

use `cement_manage`;

--  用户
drop table if exists `user`;
create table user( 	
	id int primary key auto_increment,
    username varchar(20) not null unique comment '用户账号',
    password varchar(20) not null comment '密码',
    identity_type int not null comment '身份类型,1代表管理员,2代表用户',
    nickname varchar(20) comment '用户昵称',
    email varchar(50) comment '邮箱',
    sex varchar(4) comment '性别',
    age int comment '年龄',
    head varchar(255) comment '头像url',
    create_time timestamp default NOW() comment '创建时间'
) comment '用户表';


-- 原材料入库记录
drop table if exists `materialrecord`;
create table if not exists `materialrecord`
(
	id int primary key auto_increment comment '材料编号',
	name varchar(128) not null comment '材料名称',
	type varchar(256) default '暂无' not null comment '材料型号',
	stock int not null comment '材料数量',
	unit varchar(12) not null comment '单位',
	price int  comment '材料价格，单位：元',
	create_time timestamp default NOW() comment '创建时间'
) comment '原材料入库记录表';

-- 原材料表
drop table if exists `materials`;
create table if not exists `materials`
(
	id int primary key auto_increment comment '编号',
	name varchar(128) not null comment '名称',
	type varchar(256) default '暂无' not null comment '型号',
	instock int default 0 comment '入库数量',
	stock int default 0 comment '剩余数量',
	usestock int default 0 comment '使用数量',
	unit varchar(12) not null comment '单位',
	price int default 0 comment '入库总金额',
	create_time timestamp default NOW() comment '创建时间'
) comment '原材料库存表';

-- 原材料月表
drop table if exists `materialsmonth`;
create table if not exists `materialsmonth`
(
	id int primary key auto_increment comment '编号',
	name varchar(128) not null comment '名称',
	type varchar(256) default '暂无' not null comment '型号',
	instock int default 0 comment '入库数量',
	usestock int default 0 comment '使用数量',
	unit varchar(12) not null comment '单位',
	price int default 0 comment '入库总金额',
	create_time varchar(64) comment '创建时间'
) comment '原材料月表';

-- 原材料出库记录
drop table if exists `materialuse`;
create table if not exists `materialuse`
(
	id int primary key auto_increment comment '材料编号',
	name varchar(128) not null comment '材料名称',
	type varchar(256) default '暂无' not null comment '材料型号',
	usestock int not null comment '材料使用数量',
	stock int not null comment '材料库存数量',
	unit varchar(12) not null comment '单位',
	create_time timestamp default NOW() comment '创建时间'
) comment '原材料使用记录表';


-- 产品入库记录
drop table if exists `goodrecord`;
create table if not exists `goodrecord`
(
	id int primary key auto_increment comment '编号',
	name varchar(128) not null comment '产品名称',
	type varchar(256) default '暂无' not null comment '产品型号',
	stock int not null comment '产品数量',
	unit varchar(12) not null comment '单位',
	create_time timestamp default NOW() comment '创建时间'
) comment '产品入库记录';

-- 产品总表
drop table if exists `goods`;
create table if not exists `goods`
(
	id int primary key auto_increment comment '编号',
	name varchar(128) not null comment '产品名称',
	type varchar(256) default '暂无' not null comment '产品型号',
	instock int default 0 comment '产品入库总数量',
	sellstock int default 0 comment '产品售出总数量',
	stock int default 0 comment '产品库存',
	unit varchar(12) not null comment '单位',
	price int default 0 comment '售出总金额',
	create_time timestamp default NOW() comment '创建时间'
) comment '产品总表';

-- 产品月表
drop table if exists `goodsmonth`;
create table if not exists `goodsmonth`
(
	id int primary key auto_increment comment '编号',
	name varchar(128) not null comment '名称',
	type varchar(256) default '暂无' not null comment '产品型号',
	instock int default 0 comment '产品入库总数量',
	sellstock int default 0 comment '产品售出总数量',
	unit varchar(12) not null comment '单位',
	price int default 0 comment '售出总金额',
	create_time varchar(64) comment '创建时间'
) comment '产品月表';

-- 产品售出记录表
drop table if exists `goodsell`;
create table if not exists `goodsell`
(
	id varchar(32) primary key comment '编号',
	allprice int default 0 comment '售出总金额,单位：元',
	purchaser varchar(256) default '暂无' not null comment '购买方',
	create_time timestamp default NOW() comment '创建时间'
) comment '产品售出表';


-- 产品售出项
drop table if exists `goodsell_item`;
create table if not exists `goodsell_item`
(
	id int primary key auto_increment comment '编号',
	goodsell_id varchar(32)  not null comment '记录表编号',
	goods_id int not null comment '产品编号',
	goods_name varchar(128) not null comment '产品名称',
	goods_type varchar(256) default '暂无' not null comment '产品型号',
	goods_sellstock int not null comment '售出数量',
	goods_unit varchar(12) not null comment '单位',
    goods_price int not null comment '产品价格，单位：元'
) comment '产品售出项';


```



## 用户注册

**注意事项**

- 用户名（手机号）独一无二
- 密码大于等于6位数，小于等于20位













