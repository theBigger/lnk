CREATE DATABASE IF NOT EXISTS `mos-lnk` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `lnk-user` (
  `mid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户唯一ID',
  `party_id` varchar(64) NOT NULL COMMENT '第三方系统账号ID',
  `nick` varchar(128) NOT NULL COMMENT '用户昵称',
  `passwd` varchar(128) NOT NULL COMMENT '用户密码',
  `avatar` varchar(256) DEFAULT NULL COMMENT '用户头像',
  `weixin` varchar(64) DEFAULT NULL COMMENT '微信',
  `qq` varchar(64) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(128) DEFAULT NULL COMMENT 'email',
  `telephone` varchar(45) DEFAULT NULL COMMENT '电话号码',
  `phone` varchar(45) DEFAULT NULL COMMENT '固话',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `ip` varchar(45) DEFAULT NULL COMMENT 'ip',
  `lng` double DEFAULT NULL COMMENT '经度',
  `lat` double DEFAULT NULL COMMENT '纬度',
  `status` varchar(10) NOT NULL COMMENT '户当前状态',
  `extend` varchar(512) DEFAULT NULL COMMENT '用户扩展信息',
  `gmt_created` datetime NOT NULL COMMENT '用户注册时间',
  `gmt_modified` datetime NOT NULL COMMENT '用户最后修改信息时间',
  PRIMARY KEY (`mid`),
  UNIQUE KEY `mid_UNIQUE` (`mid`),
  UNIQUE KEY `party_id_UNIQUE` (`party_id`),
  KEY `nick` (`nick`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lnk-message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mid` bigint(20) NOT NULL COMMENT '发起报文的用户的唯一ID',
  `party_id` varchar(64) NOT NULL COMMENT '第三方系统账号ID',
  `nick` varchar(128) NOT NULL COMMENT '用户昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '用户头像',
  `tid` bigint(20) NOT NULL COMMENT '消息到达方的用户的唯一ID',
  `body` varchar(2048) NOT NULL COMMENT '消息内容体',
  `gmt_created` bigint(20) NOT NULL COMMENT '消息发送时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `tid` (`tid`),
  KEY `mid` (`mid`),
  KEY `mtid` (`mid`,`tid`),
  KEY `time` (`gmt_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lnk-subscribe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mid` bigint(20) NOT NULL COMMENT '订阅用户唯一ID',
  `smid` bigint(20) NOT NULL COMMENT '被订阅用户唯一ID',
  `party_id` varchar(45) DEFAULT NULL COMMENT '被订阅用户第三方系统账号ID',
  `nick` varchar(45) DEFAULT NULL COMMENT '被订阅用户昵称',
  `avatar` varchar(45) DEFAULT NULL COMMENT '被订阅用户头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `smid` (`mid`,`smid`),
  KEY `sid` (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


