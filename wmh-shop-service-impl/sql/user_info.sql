/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.75.128
 Source Server Type    : MySQL
 Source Server Version : 50647
 Source Host           : 192.168.75.128:3306
 Source Schema         : wmh

 Target Server Type    : MySQL
 Target Server Version : 50647
 File Encoding         : 65001

 Date: 15/03/2020 16:39:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `USER_ID` int(12) NOT NULL AUTO_INCREMENT COMMENT 'user_id',
  `MOBILE` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `PASSWORD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `SEX` tinyint(1) NULL DEFAULT 0 COMMENT '性别  1男  2女',
  `AGE` tinyint(3) NULL DEFAULT 0 COMMENT '年龄',
  `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `IS_AVALIBLE` tinyint(1) NULL DEFAULT 1 COMMENT '是否可用 1正常  2冻结',
  `PIC_IMG` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `QQ_OPEN_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ联合登陆id',
  `WX_OPEN_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信公众号关注id',
  `CREATE_TIME` timestamp(0) NULL DEFAULT NULL COMMENT '注册时间',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`USER_ID`) USING BTREE,
  UNIQUE INDEX `MOBILE_UNIQUE`(`MOBILE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户会员表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
