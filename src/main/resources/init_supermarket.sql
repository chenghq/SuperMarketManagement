-- 新建存储产品的数据库
CREATE TABLE `PRODUCTS` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `type` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 保存用户ID
CREATE TABLE `USER_IPHONE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `iphone_number` varchar(11) NOT NULL,
  `mask` int(11) NOT NULL,
  `mask_date` timestamp NULL DEFAULT NULL COMMENT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- 用户添加的地址管理
CREATE TABLE `ADDRESS` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `is_default` varchar(11) NOT NULL DEFAULT 'false',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
