-- ----------------------------
-- 权限表
-- 包括用户权限（用户可拥有权限），系统管理权限，资源管理权限，用户（后台管理，机构，个人）
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(40) DEFAULT NULL COMMENT '名称',
    `parent_name` varchar(40) DEFAULT NULL COMMENT '组名',
    `url` varchar(255) DEFAULT NULL COMMENT '权限url',
    `attribute` int(2) DEFAULT NULL COMMENT '是否为管理员的权限',
    `serial` int(2) DEFAULT NULL COMMENT '节点序号',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 角色表
-- 将权限赋予角色，把角色赋予用户。
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(40) DEFAULT NULL COMMENT '名称',
    `status` int(2) DEFAULT NULL COMMENT '是否禁用',
    `attribute` int(2) DEFAULT NULL COMMENT '是否为管理员的权限',
    `remark` varchar(255) DEFAULT NULL COMMENT '说明',
    `used` int(2) DEFAULT NULL COMMENT '是否被使用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 角色权限表
-- 角色权限关系表
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
    `role_id` int(11) NOT NULL COMMENT '角色id',
    `permission_id` int(11) NOT NULL COMMENT '权限id',
    PRIMARY KEY (`role_id`,`permission_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 管理员用户
-- 后台管理用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(40) NOT NULL COMMENT '账户',
    `pwd` varchar(200) NOT NULL COMMENT '密码',
    `realname` varchar(20) DEFAULT NULL COMMENT '真实姓名',
    `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
    `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
    `address` varchar(255) DEFAULT NULL COMMENT '地址',
    `gmt_create` datetime  DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
    `role_id` int(11) DEFAULT NULL COMMENT '角色id',
    `status` int(2) NOT NULL COMMENT '是否禁用',
    `organizeid` int(11) DEFAULT NULL COMMENT '是否是某一机构得后台用户',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 搜索历史表
--
-- ----------------------------
DROP TABLE IF EXISTS `search_history`;
CREATE TABLE `search_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `searchname` varchar(255) DEFAULT NULL COMMENT '搜索关键字',
  `searchurl` varchar(255) DEFAULT NULL COMMENT '搜索的链接',
  `userid` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 会员用户
-- 前台会员用户表
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(40) NOT NULL COMMENT '帐号',
    `pwd` varchar(200) NOT NULL COMMENT '密码',
    `realname` varchar(20) DEFAULT NULL COMMENT '真实姓名',
    `gender` int(2) DEFAULT NULL COMMENT '性别',
    `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
    `telephone` varchar(20) DEFAULT NULL COMMENT '电话号码',
    `address` varchar(255) DEFAULT NULL COMMENT '地址',
    `education` varchar(20) DEFAULT NULL COMMENT '学历',
    `major` varchar(255) DEFAULT NULL COMMENT '专业',
    `postcode` varchar(20) DEFAULT NULL COMMENT '邮政编码',
    `organization` varchar(255) DEFAULT NULL COMMENT '单位名称',
    `role_id` int(11) NOT NULL COMMENT '权限id',
    `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
    `status` int(2) NOT NULL COMMENT '帐号状态',
    `organiza_id` int(11) NOT NULL COMMENT '组织用户id',
    `uucode` varchar(50) DEFAULT NULL COMMENT '验证码',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 机构
-- 机构表
-- ----------------------------
DROP TABLE IF EXISTS `organize`;
CREATE TABLE `organize` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) DEFAULT NULL COMMENT '组织名称',
    `address` varchar(255) DEFAULT NULL COMMENT '地址',
    `telephone` varchar(80) DEFAULT NULL COMMENT '电话号码',
    `expiration` date DEFAULT NULL COMMENT '有效日期',
    `role_id` int(11) NOT NULL COMMENT '权限id',
    `gmt_create` date DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` date DEFAULT NULL COMMENT '修改时间',
    `status` int(2) DEFAULT NULL COMMENT '状态',
    `max_online` int(11) DEFAULT NULL COMMENT '机构用户下最大并发数量',
    `file` varchar(800) DEFAULT NULL COMMENT '附加文件',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 机构用户
-- 机构和前台用户关系表
-- ----------------------------
DROP TABLE IF EXISTS `organize_user`;
CREATE TABLE `organize_user` (
    `organize_id` int(11) NOT NULL COMMENT '组织id',
    `member_id` int(11) NOT NULL COMMENT '会员id',
    `is_admin` int(2) DEFAULT NULL COMMENT '是否机构管理员',
    PRIMARY KEY (`organize_id`,`member_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 机构IP段
-- 机构的ip段
-- ----------------------------
DROP TABLE IF EXISTS `organize_ip`;
CREATE TABLE `organize_ip` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `organize_id` int(11) NOT NULL COMMENT '组织id',
    `start` varchar(255) DEFAULT NULL COMMENT '开始ip段',
    `end` varchar(255) DEFAULT NULL COMMENT '结束ip段',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 日志
-- 后台操作日志包括所有操作
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `gmt_create` datetime DEFAULT NULL COMMENT '操作日期',
    `oper_id` varchar(20) DEFAULT NULL COMMENT '操作者Id',
    `oper_username` varchar(20) DEFAULT NULL COMMENT '操作者名字',
    `ip` varchar(50) DEFAULT NULL COMMENT '操作者IP',
    `description` varchar(255) DEFAULT NULL COMMENT '说明',
    `oper_type` varchar(10) DEFAULT NULL COMMENT '操作类型',
    `target_type` varchar(10) DEFAULT NULL COMMENT '对象类型',
    `is_warn` varchar(10) DEFAULT NULL COMMENT '是否是异常操作记录，0:是  1:否',
	 PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 配置表
-- 系统配置表
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
    `name` varchar(255) NOT NULL COMMENT '配置名称',
    `value` longtext COMMENT '配置值',
    `remark` varchar(255) DEFAULT NULL COMMENT '说明',
    PRIMARY KEY (`name`),
     KEY `config_name` (`name`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 访问记录表
--
-- ----------------------------
DROP TABLE IF EXISTS `access_log`;
CREATE TABLE `access_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `name` varchar(255) DEFAULT NULL COMMENT '访问资源名称',
    `type` int(2) DEFAULT NULL COMMENT '资源类型（1电子书 2期刊 3标准）',
    `resource_id` int(11) DEFAULT NULL COMMENT '资源ID',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `ip` varchar(50) DEFAULT NULL COMMENT '用户ip',
    `url` varchar(255) DEFAULT NULL COMMENT '访问的路径',
    `gmt_create` datetime DEFAULT NULL COMMENT '创建日期',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 下载记录表
--
-- ----------------------------
DROP TABLE IF EXISTS `download_log`;
CREATE TABLE `download_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `name` varchar(255) DEFAULT NULL COMMENT '资源名称',
    `type` int(2) DEFAULT NULL COMMENT '资源类型（2期刊 3标准）',
    `resource_id` int(11) DEFAULT NULL COMMENT '资源ID',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `ip` varchar(50) DEFAULT NULL COMMENT '用户ip',
    `url` varchar(255) DEFAULT NULL COMMENT '下载路径',
    `gmt_create` datetime DEFAULT NULL COMMENT '创建日期',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- 我的收藏表
-- 前台用户我的收藏
-- ----------------------------
DROP TABLE IF EXISTS `my_collect`;
CREATE TABLE `my_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` varchar(255) DEFAULT NULL COMMENT '收藏的文章details中的zid',
	`title` varchar(255) DEFAULT NULL COMMENT '收藏的标题',
  `subtitle` varchar(255) DEFAULT NULL COMMENT '收藏文章的副标题',
  `source` varchar(255) DEFAULT NULL COMMENT '文章的来源',
  `abs` longtext DEFAULT NULL COMMENT '文章的摘要',
  `username` varchar(255) DEFAULT NULL COMMENT '收藏人的用户名',
  `foldername` varchar(255) DEFAULT NULL COMMENT '收藏的文件夹名称',
  `gmt_greate` datetime DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- ----------------------------
-- 我的关注表
-- 前台用户我的关注表
-- ----------------------------
DROP TABLE IF EXISTS `my_concern`;
CREATE TABLE `my_concern` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '关注的名字',
  `name_id` int(11) DEFAULT NULL COMMENT '关注人的ID',
  `username` varchar(255) DEFAULT NULL COMMENT '关注人的用户名',
  `gmt_create` datetime DEFAULT NULL COMMENT '关注得时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


-- ----------------------------
-- 热词表
-- 搜索的热词
-- ----------------------------
DROP TABLE IF EXISTS `hot_word`;
CREATE TABLE `hot_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(255) DEFAULT NULL,
  `gmt_create` date DEFAULT NULL,
  `gmt_modified` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 我的拼接阅读表
-- 前台用户我的拼接表
-- ----------------------------
DROP TABLE IF EXISTS `my_splice`;
CREATE TABLE `my_splice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题名称',
  `zid` varchar(255) DEFAULT NULL COMMENT '标题得zid',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `gmt_create` datetime DEFAULT NULL COMMENT '拼接时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- 节点表
-- 图标管理表
-- ----------------------------
DROP TABLE IF EXISTS `node_data`;
CREATE TABLE `node_data` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fid` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;

-- ----------------------------
-- 节点资源表(修改后资源表)
--
-- ----------------------------
DROP TABLE IF EXISTS `node_url`;
CREATE TABLE `node_url` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `node_url` varchar(255) NOT NULL,
  `node_id` int(11) NOT NULL,
  `node_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;

-- ----------------------------
-- 在线会员表
-- 在线版本的会员用户表
-- ----------------------------
DROP TABLE IF EXISTS `member_online`;
CREATE TABLE `member_online` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL COMMENT '帐号',
  `pwd` varchar(200) NOT NULL COMMENT '密码',
  `realname` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `gender` int(2) DEFAULT NULL COMMENT '性别',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `education` varchar(20) DEFAULT NULL COMMENT '学历',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `postcode` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `organization` varchar(255) DEFAULT NULL COMMENT '单位名称',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '帐号状态',
--   `organiza_id` int(11) DEFAULT NULL COMMENT '组织用户id',
--   `uucode` varchar(50) DEFAULT NULL COMMENT '验证码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;


-- ----------------------------
-- 在线机构表
-- 在线版本的机构表
-- ----------------------------
DROP TABLE IF EXISTS `organize_online`;
CREATE TABLE `organize_online` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `telephone` varchar(80) DEFAULT NULL COMMENT '电话号码',
  `expiration` date DEFAULT NULL COMMENT '有效日期',
  `role_id` int(11) NOT NULL COMMENT '权限id',
  `gmt_create` date DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` date DEFAULT NULL COMMENT '修改时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `max_online` int(11) NOT NULL COMMENT '机构用户下最大并发数量',
  `file` varchar(800) NOT NULL COMMENT '附加文件',
  `code` varchar(255) DEFAULT NULL,
  `bid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;




