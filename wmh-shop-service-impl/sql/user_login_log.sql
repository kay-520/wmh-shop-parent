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

 Date: 15/03/2020 16:39:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_time` datetime(0) NOT NULL,
  `login_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `channel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '渠道',
  `equipment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备',
  `is_availability` int(255) NOT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 721 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
