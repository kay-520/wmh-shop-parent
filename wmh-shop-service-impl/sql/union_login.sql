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

 Date: 22/03/2020 02:25:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for union_login
-- ----------------------------
DROP TABLE IF EXISTS `union_login`;
CREATE TABLE `union_login`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `union_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名称',
  `union_public_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联合登录id',
  `union_bean_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联合登录bean',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'appID',
  `app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'appKey',
  `redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  `request_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `is_availability` int(1) NULL DEFAULT NULL COMMENT '是否可用 0 不可用 1 可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of union_login
-- ----------------------------
INSERT INTO `union_login` VALUES (1, '腾讯QQ联合登陆', 'wmh_qq', 'QQUnionLoginStrategy', '101410454', 'de56b00427f5970650c4f8ee3cfcfc2d', 'http://www.itmayiedu.com:7070/loginQu/oauth/callback', 'https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101410454&redirect_uri=http://www.itmayiedu.com:7070/login/oauth/callback?unionPublicId=wmh_qq&state=1', 1);
INSERT INTO `union_login` VALUES (2, '腾讯微信联合登陆', 'wmh_weixin', NULL, '123456', '12133', NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
