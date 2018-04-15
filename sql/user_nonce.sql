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

 Date: 04/15/2018 13:47:15 PM
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `user_nonce`
-- ----------------------------
DROP TABLE IF EXISTS `user_nonce`;
CREATE TABLE `user_nonce` (
  `user_id` bigint(20) NOT NULL,
  `nonce` varchar(255) NOT NULL,
  `overdue_time` timestamp NOT NULL DEFAULT '1970-01-01 12:00:00',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
