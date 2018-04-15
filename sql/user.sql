/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : petshop

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 04/15/2018 13:47:07 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `user_name_encrypted` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `update_time` timestamp NULL DEFAULT '1970-01-01 12:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT '1970-01-01 12:00:00',
  `user_avatar` bigint(20) unsigned zerofill DEFAULT NULL,
  `user_nickname` varchar(255) DEFAULT NULL,
  `user_phone_number` varchar(20) DEFAULT NULL,
  `user_email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
