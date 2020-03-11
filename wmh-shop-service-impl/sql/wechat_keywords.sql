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

 Date: 11/03/2020 22:32:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wechat_keywords
-- ----------------------------
DROP TABLE IF EXISTS `wechat_keywords`;
CREATE TABLE `wechat_keywords`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `keyword_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wechat_keywords
-- ----------------------------
INSERT INTO `wechat_keywords` VALUES (1, 'wmh', '这是wmh的微信公众', '2020-03-10 17:32:15', '2020-03-11 17:32:18', '1');
INSERT INTO `wechat_keywords` VALUES (2, '哈哈哈', '你笑啥呢', '2020-03-10 17:32:45', '2020-03-10 17:32:49', '2');

SET FOREIGN_KEY_CHECKS = 1;
