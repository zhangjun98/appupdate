/*
 Navicat Premium Data Transfer

 Source Server         : 远程数据库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 58.220.249.164:3306
 Source Schema         : app_update

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 18/08/2020 14:37:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pkid` int(20) NOT NULL AUTO_INCREMENT COMMENT '业务id',
  `productID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '产品的标识',
  `productVersionID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '产品的版本号',
  `productFileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '产品的文件路径',
  `productFileSize` bigint(30) NOT NULL COMMENT '产品包的大小',
  `isNew` int(1) NOT NULL COMMENT '是否为最新版本 1为最新。2为历史',
  `updateMethod` int(1) NOT NULL COMMENT '更新方式 1 推荐更新 2 强制更新',
  `updateDirections` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新说明',
  `VersionCode` int(3) NOT NULL COMMENT '区分版本号的唯一标识',
  `updateDate` bigint(50) NOT NULL COMMENT '更新时间戳',
  PRIMARY KEY (`pkid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
