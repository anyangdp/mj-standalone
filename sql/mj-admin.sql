/*
 Navicat Premium Data Transfer

 Source Server         : localhost_13306
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:13306
 Source Schema         : mj-admin

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 28/03/2024 10:36:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_permission`;
CREATE TABLE `s_permission` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `show_always` int DEFAULT '0' COMMENT '0默认显示，1 只对超级管理显示',
  `level` int NOT NULL DEFAULT '1' COMMENT '层级：1，2，3',
  `type` int NOT NULL DEFAULT '0' COMMENT '类型 0顶部菜单 1页面 2具体操作',
  `title` varchar(20) DEFAULT NULL COMMENT '页面标题',
  `path` varchar(150) DEFAULT NULL COMMENT '页面路径/资源链接url',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `http_method` varchar(20) DEFAULT NULL COMMENT 'http请求方法',
  `parent_id` varchar(40) DEFAULT NULL COMMENT '父id',
  `description` varchar(20) DEFAULT NULL COMMENT '描述',
  `sort` int DEFAULT NULL COMMENT '排序',
  `created_by` varchar(40) DEFAULT NULL COMMENT '创建者',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(40) DEFAULT NULL COMMENT '更新者',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '激活标记',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `deleted_by` varchar(40) DEFAULT NULL COMMENT '删除者',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `deleted_msg` varchar(256) DEFAULT NULL COMMENT '删除原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='平台菜单/权限表';

-- ----------------------------
-- Records of s_permission
-- ----------------------------
BEGIN;
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('account', '账号信息', 0, 2, 0, '账号信息', '', NULL, NULL, 'home', NULL, 1, NULL, '2023-02-06 09:54:29', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('bbbb3', '按钮3', 0, 3, 2, '按钮3', '', NULL, NULL, 'test2', NULL, 1, NULL, '2023-01-30 13:43:34', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('console', '控制台', 0, 2, 0, '控制台', '', NULL, NULL, 'home', NULL, 1, NULL, '2023-02-06 09:53:48', NULL, '2023-02-06 15:08:55', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('del', '测试按钮2', 0, 3, 2, '按钮2', '/s/del', '', 'DELETE', 'test2', NULL, 1, NULL, '2023-01-30 13:42:24', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('home', '首页', 0, 1, 1, '首页', '', NULL, NULL, '', NULL, 100, NULL, '2023-01-30 09:57:30', NULL, '2023-02-06 16:20:46', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('inset', '按钮1', 0, 3, 2, '按钮1', '/s/add', NULL, 'POST', 'test1', NULL, 1, NULL, '2023-01-30 13:32:46', NULL, '2023-02-06 15:08:55', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('permission-manage', '菜单管理', 0, 2, 0, '菜单管理', '', NULL, NULL, 'setting', NULL, 69, NULL, '2023-02-07 10:40:31', NULL, '2023-02-07 10:40:31', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('setting', '配置目录', 0, 1, 1, '配置', '', NULL, NULL, '', NULL, 70, NULL, '2023-02-06 09:56:54', NULL, '2023-02-06 16:20:46', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('system-setting', '系统设置', 0, 2, 0, '系统设置', '', NULL, NULL, 'setting', NULL, 1, NULL, '2023-02-06 09:59:37', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('tenant-manage', '租户管理', 0, 2, 0, '租户管理', '', NULL, NULL, 'setting', NULL, 1, NULL, '2023-02-06 10:00:21', NULL, '2023-02-06 15:08:55', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('test1', '测试子页面1', 0, 2, 0, '测试子页面1', '', '', NULL, 'test', NULL, 1, NULL, '2023-01-30 13:14:49', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('test2', '测试子页面2', 0, 2, 0, '测试子页面2', '', NULL, NULL, 'test', NULL, 1, NULL, '2023-01-30 13:15:31', NULL, '2023-02-06 15:08:54', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_permission` (`id`, `name`, `show_always`, `level`, `type`, `title`, `path`, `icon`, `http_method`, `parent_id`, `description`, `sort`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('user-manage', '用户管理', 0, 2, 0, '用户管理', '', NULL, NULL, 'setting', NULL, 1, NULL, '2023-02-06 10:00:02', NULL, '2023-02-06 15:08:55', 1, 0, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` varchar(40) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(20) DEFAULT NULL,
  `created_by` varchar(40) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT (now()),
  `updated_by` varchar(40) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` tinyint DEFAULT NULL,
  `deleted` tinyint DEFAULT NULL,
  `deleted_by` varchar(40) DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `deleted_msg` varchar(256) DEFAULT NULL,
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- ----------------------------
-- Records of s_role
-- ----------------------------
BEGIN;
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('929092833745305600', '角色01', '', NULL, '2023-12-04 16:44:19', '805397896395489280', '2023-12-04 16:44:19', 1, 0, NULL, NULL, NULL, 0);
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('929093103309029376', '角色02', '', NULL, '2023-12-04 16:45:23', '805397896395489280', '2023-12-04 16:45:23', 1, 0, NULL, NULL, NULL, 0);
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('929093498177585152', '角色1', '', NULL, '2023-12-04 16:48:25', NULL, '2023-12-04 16:48:25', 1, 0, NULL, NULL, NULL, 0);
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('929096552931131392', '角色3', '', NULL, '2023-12-04 16:59:42', NULL, '2023-12-14 16:59:27', 1, 0, NULL, NULL, NULL, 0);
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('929096712939634688', '角色2', '', NULL, '2023-12-04 17:02:16', '805397896395489280', '2023-12-11 15:53:11', 0, 0, NULL, NULL, NULL, 0);
INSERT INTO `s_role` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`, `sort`) VALUES ('931544303077949440', '张三', '', '805397896395489280', '2023-12-11 11:05:34', '805397896395489280', '2023-12-11 11:15:05', 1, 0, NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for s_role_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_role_menu_permission`;
CREATE TABLE `s_role_menu_permission` (
  `role_id` varchar(40) DEFAULT NULL,
  `permission_id` varchar(80) DEFAULT NULL,
  UNIQUE KEY `s_role_menu_permission_role_id_permission_id_uindex` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of s_role_menu_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for s_role_user
-- ----------------------------
DROP TABLE IF EXISTS `s_role_user`;
CREATE TABLE `s_role_user` (
  `role_id` varchar(40) NOT NULL COMMENT '角色id',
  `user_id` varchar(40) NOT NULL COMMENT '用户id',
  UNIQUE KEY `s_role_user_role_id_user_id_uindex` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色用户';

-- ----------------------------
-- Records of s_role_user
-- ----------------------------
BEGIN;
INSERT INTO `s_role_user` (`role_id`, `user_id`) VALUES ('929096552931131392', '817026035840188416');
COMMIT;

-- ----------------------------
-- Table structure for s_tenant
-- ----------------------------
DROP TABLE IF EXISTS `s_tenant`;
CREATE TABLE `s_tenant` (
  `id` varchar(40) NOT NULL COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '租户名称',
  `description` varchar(20) DEFAULT NULL COMMENT '描述',
  `created_by` varchar(40) DEFAULT NULL COMMENT '创建者',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(40) DEFAULT NULL COMMENT '更新者',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '激活标记',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `deleted_by` varchar(40) DEFAULT NULL COMMENT '删除者',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `deleted_msg` varchar(256) DEFAULT NULL COMMENT '删除原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='租户';

-- ----------------------------
-- Records of s_tenant
-- ----------------------------
BEGIN;
INSERT INTO `s_tenant` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('818114658790539264', '测试租户1', '121', NULL, '2023-02-01 10:56:40', NULL, '2023-02-01 16:18:34', 1, 0, NULL, NULL, NULL);
INSERT INTO `s_tenant` (`id`, `name`, `description`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('818194944727449600', '测试租户2', '123', NULL, '2023-02-01 16:15:41', NULL, '2023-02-01 16:18:17', 1, 1, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_tenant_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_tenant_resource`;
CREATE TABLE `s_tenant_resource` (
  `tenant_id` varchar(40) NOT NULL COMMENT '租户id',
  `type` varchar(40) NOT NULL COMMENT '资产类型',
  `resource_id` varchar(40) NOT NULL COMMENT '资产id',
  KEY `s_tenant_resource_tenant_id_type_index` (`tenant_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='租户资产（数据权限）';

-- ----------------------------
-- Records of s_tenant_resource
-- ----------------------------
BEGIN;
INSERT INTO `s_tenant_resource` (`tenant_id`, `type`, `resource_id`) VALUES ('818114658790539264', 'device', '776138534573572096');
INSERT INTO `s_tenant_resource` (`tenant_id`, `type`, `resource_id`) VALUES ('818114658790539264', 'device', '776138711451566080');
COMMIT;

-- ----------------------------
-- Table structure for s_tenant_user
-- ----------------------------
DROP TABLE IF EXISTS `s_tenant_user`;
CREATE TABLE `s_tenant_user` (
  `tenant_id` varchar(40) NOT NULL COMMENT '租户id',
  `user_id` varchar(40) NOT NULL COMMENT '用户id',
  KEY `s_tenant_user_tenant_id_user_id_index` (`tenant_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='租户用户';

-- ----------------------------
-- Records of s_tenant_user
-- ----------------------------
BEGIN;
INSERT INTO `s_tenant_user` (`tenant_id`, `user_id`) VALUES ('818114658790539264', '807956359726235648');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `username` varchar(256) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `nickname` varchar(256) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `created_by` varchar(40) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(40) DEFAULT NULL COMMENT '更新者',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '激活标记',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `deleted_by` varchar(40) DEFAULT NULL COMMENT '删除者',
  `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `deleted_msg` varchar(256) DEFAULT NULL COMMENT '删除原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `avatar`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('805397896395489280', 'admin', '$2a$10$5dnKhdHrFNFOJp8ghinBOu4pq0D8n2qnGqILapFF92WSz9E5aKjqu', '超级管理员', NULL, NULL, '2022-12-28 08:44:49', NULL, '2022-12-28 08:44:49', 1, 0, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `avatar`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('807956359726235648', 'test1', '$2a$10$k2wUNvZPYfOgDJDCXwNAw.GD9HdIZKWYbCsKlmFAFD/tk9glOA1xe', '普通用户1', 'http://localhost:9201/iot/admin/file/2023-01-29/817025198304788480.jpg', NULL, '2023-01-04 10:11:13', NULL, '2023-02-13 14:56:14', 1, 0, NULL, NULL, NULL);
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `avatar`, `created_by`, `created_at`, `updated_by`, `updated_at`, `active`, `deleted`, `deleted_by`, `deleted_at`, `deleted_msg`) VALUES ('817026035840188416', 'test2', '$2a$10$k2wUNvZPYfOgDJDCXwNAw.GD9HdIZKWYbCsKlmFAFD/tk9glOA1xe', '普通用户2', 'http://localhost:9201/iot/admin/file/2023-01-29/817025937932550144.jpg', NULL, '2023-01-29 10:50:52', NULL, '2023-12-14 15:49:47', 1, 0, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_menu_permission`;
CREATE TABLE `user_menu_permission` (
  `user_id` varchar(40) NOT NULL COMMENT '用户id',
  `permission_id` varchar(40) NOT NULL COMMENT '权限id',
  KEY `user_menu_permission_user_id_permission_id_index` (`user_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户菜单权限关联表';

-- ----------------------------
-- Records of user_menu_permission
-- ----------------------------
BEGIN;
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'account');
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'console');
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'device');
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'device-manage');
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'home');
INSERT INTO `user_menu_permission` (`user_id`, `permission_id`) VALUES ('807956359726235648', 'product');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
