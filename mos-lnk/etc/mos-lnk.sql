
CREATE DATABASE IF NOT EXISTS `mos_lnk` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `lnk_user` (
  `mid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户唯一ID',
  `party_id` varchar(64) NOT NULL COMMENT '第三方系统账号ID',
  `push_id` varchar(256) DEFAULT NULL COMMENT '推送ID',
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
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `status` varchar(10) NOT NULL COMMENT '户当前状态',
  `extend` varchar(512) DEFAULT NULL COMMENT '用户扩展信息',
  `gmt_created` datetime NOT NULL COMMENT '用户注册时间',
  `gmt_modified` datetime NOT NULL COMMENT '用户最后修改信息时间',
  PRIMARY KEY (`mid`),
  UNIQUE KEY `mid_UNIQUE` (`mid`),
  UNIQUE KEY `party_id_UNIQUE` (`party_id`),
  KEY `nick` (`nick`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lnk_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mid` bigint(20) NOT NULL COMMENT '发起报文的用户的唯一ID',
  `party_id` varchar(64) NOT NULL COMMENT '发起报文的用户的第三方系统账号ID',
  `nick` varchar(128) NOT NULL COMMENT '发起报文的用户昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '发起报文的用户头像',
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

CREATE TABLE `lnk_group_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `mid` bigint(20) NOT NULL COMMENT '发起报文的用户的唯一ID',
  `party_id` varchar(64) NOT NULL COMMENT '发起报文的用户的第三方系统账号ID',
  `nick` varchar(128) NOT NULL COMMENT '发起报文的用户昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '发起报文的用户头像',
  `group_id` bigint(20) NOT NULL COMMENT '消息到达方的聊天组的唯一ID',
  `tid` bigint(20) NOT NULL COMMENT '消息到达方的用户的唯一ID',
  `body` varchar(2048) NOT NULL COMMENT '消息内容体',
  `gmt_created` bigint(20) NOT NULL COMMENT '消息发送时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `group_id` (`group_id`),
  KEY `mid` (`mid`),
  KEY `mgid` (`mid`,`group_id`),
  KEY `time` (`gmt_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lnk_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) NOT NULL COMMENT '聊天组名称',
  `owner_mid` bigint(20) NOT NULL COMMENT '创建者唯一ID',
  `tags` varchar(256) NOT NULL COMMENT '聊天组标签',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后修改信息时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `owner_mid` (`owner_mid`),
  KEY `time` (`gmt_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lnk_join_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` bigint(20) NOT NULL COMMENT '聊天组唯一ID',
  `mid` bigint(20) NOT NULL COMMENT '用户的唯一ID',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `group_id` (`group_id`),
  KEY `mgid` (`mid`,`group_id`),
  KEY `time` (`gmt_created`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `lnk_user` VALUES 
(1,'1','000','王小米','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:39744/null',0,0,'offline','','2015-09-24 17:55:03','2015-09-27 00:19:18'),
(2,'2','000','15137179209','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:52576/null',0,0,'offline','','2015-09-24 17:58:46','2015-09-28 15:16:36'),
(3,'3','000','15652500002','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:54432/null',0,0,'offline','','2015-09-24 17:59:28','2015-09-24 17:59:27'),
(4,'4','000','18238811103','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:56736/null',0,0,'offline','','2015-09-24 18:00:06','2015-09-24 18:00:05'),
(5,'5','000','刘飞','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:59648/null',0,0,'offline','','2015-09-24 18:00:46','2015-09-27 00:00:45'),
(6,'6','000','王洋','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:61024/null',0,0,'offline','','2015-09-24 18:01:19','2015-09-24 18:01:19'),
(7,'7','000','杨贵松','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:62432/null',0,0,'offline','','2015-09-24 18:01:50','2015-09-24 18:01:50'),
(8,'8','000','杨贵松','123456','http://123.57.55.59:8080/resources/images/default_avatar.png',NULL,NULL,NULL,NULL,NULL,'','/180.166.49.54:64416/null',0,0,'offline','','2015-09-24 18:02:33','2015-09-24 18:02:33'),
(9,'10','000','客服小秘书','123456','http://123.57.55.59:8081/assets/image/fei.liu.jpg',NULL,NULL,NULL,NULL,NULL,'','/120.24.228.100:34977/null',0,0,'offline','','2015-09-24 18:31:49','2015-09-24 18:31:49')
;




