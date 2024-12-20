drop database if exists nmms;
create database nmms;
use nmms;

create table sys_user(
	user_id int primary key auto_increment COMMENT '用户主键',
	user_name varchar(20) COMMENT '用户姓名',
	login_name varchar(20) COMMENT '账号',
	password varchar(50) COMMENT '密码',
	phone varchar(20) COMMENT '手机号码',
	address varchar(30) COMMENT '联系地址',
	is_valid int COMMENT '账号是否正常使用,0:已注销 1:正常使用',
	regist_date datetime COMMENT '注册时间'
)engine=Innodb default charset=utf8 COMMENT '前台用户表';

create table sys_staff (
  staff_id int primary key AUTO_INCREMENT COMMENT '主键',
  staff_name varchar(50) COMMENT '员工姓名',
  login_name varchar(50) COMMENT '员工账号',
  password varchar(50) COMMENT '密码',
  phone varchar(50) COMMENT '手机号码',
  email varchar(100) COMMENT '邮箱',
  dept_id int COMMENT '所属部门',
  role varchar(10) COMMENT '角色,1001:系统管理员,1002: 普通管理员',
  is_valid int COMMENT '是否在职,0:离职 1:在职',
  create_date datetime COMMENT '入职时间',
  create_staff_id int(8) COMMENT '办理入职人员'
)engine=Innodb default charset=utf8 COMMENT '员工表';


create table sys_dept (
  dept_id int primary key AUTO_INCREMENT COMMENT '主键',
  dept_name varchar(50) COMMENT '部门名',
  dept_no varchar(20) COMMENT '部门编号',
  father_dept_id int COMMENT '上级部门',
  remark varchar(1024) COMMENT '部门职能',
  create_date datetime COMMENT '部门创建时间',
  create_staff_id int COMMENT '部门创建人',
  is_valid int COMMENT '部门是否正常使用 0:不再使用 1:正常使用'
)engine=Innodb default charset=utf8 COMMENT '部门表';


create table sys_attache(
	attache_id int primary key auto_increment COMMENT '主键',
	file_type varchar(10) COMMENT '文件类型',
	file_path varchar(100) COMMENT '文件路径',
	create_date datetime COMMENT '上传时间',
	user_id int COMMENT '上传者'
)engine=Innodb default charset=utf8 COMMENT '附件表';

create table sys_product_type(
	id int primary key auto_increment COMMENT '主键',
	name varchar(20) COMMENT '类型名称',
	status int COMMENT '类型状态,0:禁用 1:启用'
)engine=Innodb default charset=utf8 COMMENT '商品类型表';

create table sys_product(
	product_id int primary key auto_increment COMMENT '主键',
	product_no varchar(20) COMMENT '商品编号,要求长度固定,末尾数字位的值每次自增1',
	name varchar(20) COMMENT '商品名称',
	price double COMMENT '商品单价',
	image varchar(200) COMMENT '商品图片',
	product_type_id int COMMENT '商品类型'
)engine=Innodb default charset=utf8 COMMENT '商品表';


create table sys_order(
	id int primary key auto_increment COMMENT '主键',
	no varchar(30) COMMENT '订单号',
	price double COMMENT '订单总价',
	user_id int COMMENT '订单所属用户'
)engine=Innodb default charset=utf8 COMMENT '订单表';

create table sys_item(
	id int primary key auto_increment COMMENT '主键',
	product_id int COMMENT '明细商品',
	num int COMMENT '商品数量',
	order_id int COMMENT '所属订单',
	price double COMMENT '明细总价'
)engine=Innodb default charset=utf8 COMMENT '明细表';

create table sys_sequence(
	id int primary key auto_increment COMMENT '主键',
	name varchar(20) COMMENT '标志位',
	value varchar(20) COMMENT '序列号值'
)engine=Innodb default charset=utf8 COMMENT '序列号表';

