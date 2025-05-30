/*
SQLyog Trial v13.2.1 (64 bit)
MySQL - 8.0.29 : Database - oa_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `oa_demo`;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL COMMENT '部门名称',
  `parent_id` bigint NOT NULL COMMENT '父部门id，0表示根部门',
  `user_id` bigint DEFAULT NULL COMMENT '部门负责人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `department` */

insert  into `department`(`id`,`name`,`parent_id`,`user_id`) values 
(1,'宇宙科技有限公司',0,NULL),
(2,'系统研发部',1,NULL),
(5,'人事部',1,NULL),
(6,'市场部',1,NULL),
(8,'法务部',1,NULL),
(10,'开发一组',2,NULL),
(11,'开发二组',2,NULL),
(12,'开发三组',2,NULL),
(13,'OA项目部',12,NULL),
(14,'web开发组',11,NULL),
(15,'搜索开发组',11,NULL);

/*Table structure for table `interface` */

DROP TABLE IF EXISTS `interface`;

CREATE TABLE `interface` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '接口名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '接口描述',
  `path` varchar(255) NOT NULL COMMENT '接口地址',
  `method` varchar(10) NOT NULL COMMENT 'http 方法',
  `mode_view` tinyint(1) NOT NULL COMMENT '是否查看类型接口',
  `mode_add` tinyint(1) NOT NULL COMMENT '是否添加类型接口',
  `mode_edit` tinyint(1) NOT NULL COMMENT '是否修改类型接口',
  `mode_del` tinyint(1) NOT NULL COMMENT '是否删除类型接口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='提供给前端页面的接口';

/*Data for the table `interface` */

insert  into `interface`(`id`,`name`,`desc`,`path`,`method`,`mode_view`,`mode_add`,`mode_edit`,`mode_del`) values 
(1,'新增部门','新增部门时候使用的接口','/department/add','post',0,1,0,0),
(2,'获取员工列表','获取用户列表接口','/user/pageList','get',1,0,0,0),
(3,'修改员工信息','','/user/edit','post',0,0,1,0),
(4,'获取角色列表','','/role/list','get',1,0,0,0),
(5,'添加角色','','/role/add','post',0,1,0,0),
(6,'修改角色','','/role/edit','post',0,0,1,0),
(7,'修改角色权限','','/role/menu/permit','post',0,0,1,0),
(8,'删除角色','','/role/del','delete',0,0,0,1),
(9,'获取菜单树','','/menu/tree','get',1,0,0,0);

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL COMMENT '菜单名称',
  `parent_id` int NOT NULL COMMENT '父菜单id(0表示根菜单)',
  `icon` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'icn图标',
  `path` varchar(30) DEFAULT NULL COMMENT '路由',
  `weight` int NOT NULL COMMENT '权重，前端按照权重由高到低排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

/*Data for the table `menu` */

insert  into `menu`(`id`,`name`,`parent_id`,`icon`,`path`,`weight`) values 
(1,'root',0,NULL,NULL,0),
(2,'组织架构',1,'users',NULL,0),
(3,'日常办公',1,'calendar',NULL,0),
(4,'系统管理',1,'cog',NULL,0),
(5,'员工管理',2,'User','/employee',0),
(6,'部门管理',2,'users','/department',0),
(7,'提交申请',3,'file-alt',NULL,0),
(8,'审批待办',3,'check',NULL,0),
(9,'菜单管理',4,'bars','/menu',0),
(10,'角色管理',4,'user-shield','/role',0),
(11,'首页',1,'home','/',99),
(19,'接口管理',4,'plug','/interface',0),
(20,'权限管理',4,'lock','/permission',0);

/*Table structure for table `menu_interface` */

DROP TABLE IF EXISTS `menu_interface`;

CREATE TABLE `menu_interface` (
  `menu_id` int unsigned NOT NULL COMMENT '菜单id',
  `interface_id` int unsigned NOT NULL COMMENT '接口id',
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_key` (`menu_id`,`interface_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单和接口的绑定关系';

/*Data for the table `menu_interface` */

insert  into `menu_interface`(`menu_id`,`interface_id`,`id`) values 
(5,2,34),
(5,3,35),
(6,1,26),
(10,4,36),
(10,5,37),
(10,6,38),
(10,7,39),
(10,8,40),
(11,9,41);

/*Table structure for table `position` */

DROP TABLE IF EXISTS `position`;

CREATE TABLE `position` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '英文标识',
  `name` varchar(10) NOT NULL COMMENT '中文名称',
  `level` tinyint NOT NULL COMMENT '级别，数字越大越高',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='职位表';

/*Data for the table `position` */

insert  into `position`(`id`,`key`,`name`,`level`) values 
(1,'manager','经理',3),
(2,'employee','员工',1),
(3,'supervisor','主管',2),
(4,'high_manager','高级经理',4),
(5,'director','总监',5),
(6,'vice_president','副总裁',6),
(7,'president','总裁',7);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(15) NOT NULL,
  `name` varchar(15) NOT NULL,
  `menu_permissions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '角色菜单权限设置',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`key`,`name`,`menu_permissions`) values 
(1,'admin','管理员','[{\"menuId\":11,\"view\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"menuId\":9,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":10,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":19,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":20,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true}]'),
(2,'user','普通用户','[{\"menuId\":11,\"view\":true,\"add\":false,\"edit\":false,\"delete\":false},{\"menuId\":7,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":8,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true}]'),
(3,'root','超级管理员','[{\"menuId\":11,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":2,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":5,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":6,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":3,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":7,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":8,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":4,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":9,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":10,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":19,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":20,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true}]'),
(4,'supper_user','超级用户','[{\"menuId\":5,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":6,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":7,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":8,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true}]'),
(11,'guest','匿名访客','[{\"menuId\":11,\"view\":true,\"add\":false,\"edit\":false,\"delete\":false}]'),
(12,'test','测试人员','[{\"menuId\":5,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true},{\"menuId\":6,\"view\":true,\"add\":true,\"edit\":true,\"delete\":true}]');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(12) NOT NULL COMMENT '手机号',
  `dept_id` bigint DEFAULT NULL COMMENT '所属部门',
  `ex_depts` varchar(255) DEFAULT NULL COMMENT '兼职部门',
  `position_id` int DEFAULT NULL COMMENT '职位',
  `role_ids` text COMMENT '角色id数组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`phone`,`dept_id`,`ex_depts`,`position_id`,`role_ids`) values 
(1,'超级管理员','15651001234',10,NULL,3,'[1,11,3]'),
(2,'普通员工','11355012849',5,NULL,6,'[2]'),
(3,'11','08680832353',13,NULL,5,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
