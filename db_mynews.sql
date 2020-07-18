/*
Navicat MySQL Data Transfer

Source Server         : 39.96.59.27_3306
Source Server Version : 50727
Source Host           : 39.96.59.27:3306
Source Database       : db_mynews

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-07-18 19:30:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collectId` varchar(255) NOT NULL,
  `userId` varchar(255) DEFAULT NULL COMMENT '用户编号',
  `newId` varchar(255) DEFAULT NULL COMMENT '新闻编号',
  PRIMARY KEY (`collectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for history
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `historyId` varchar(255) NOT NULL,
  `userId` varchar(30) DEFAULT NULL COMMENT '用户编号',
  `newId` varchar(255) DEFAULT NULL COMMENT '新闻编号',
  `browsCount` varchar(255) DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for newsinfo
-- ----------------------------
DROP TABLE IF EXISTS `newsinfo`;
CREATE TABLE `newsinfo` (
  `newsId` varchar(255) NOT NULL COMMENT '新闻编号',
  `title` varchar(255) DEFAULT NULL COMMENT '新闻标题',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `time` varchar(255) DEFAULT NULL COMMENT '发布时间',
  `type` varchar(255) DEFAULT NULL COMMENT '类型',
  `detail` text COMMENT '详情',
  `imgUrl` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `browseCount` int(11) DEFAULT NULL COMMENT '浏览量',
  PRIMARY KEY (`newsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userId` varchar(20) NOT NULL COMMENT '自增id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
