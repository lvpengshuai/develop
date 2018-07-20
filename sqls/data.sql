/*基础角色*/
INSERT INTO `role` VALUES ('1', '系统管理员', '0', '1', '后台-管理员全系统', '1');

/*基础数据*/
INSERT INTO `permission`  VALUES ('1', '管理用户', '用户', '/admin-user', '1', '1');
INSERT INTO `permission`  VALUES ('2', '机构用户', '用户', '/organize', '1', '2');
INSERT INTO `permission`  VALUES ('3', '个人用户', '用户', '/member-user', '1', '3');
INSERT INTO `permission`  VALUES ('4', '操作日志', '日志', '/operate-record', '1', '4');
INSERT INTO `permission`  VALUES ('6', '角色管理', '系统管理', '/role', '1', '6');
INSERT INTO `permission`  VALUES ('8', '热词管理', '系统管理', '/hot/word', '1', '8');
INSERT INTO `permission`  VALUES ('10', '图表管理', '系统管理', '/node', '1', '10');
INSERT INTO `permission`  VALUES ('11', '数据录入', '系统管理', '/admin-epub', '1', '11');
INSERT INTO `permission`  VALUES ('12', '拼接', '用户权限', '', '2', '12');
INSERT INTO `permission`  VALUES ('13', '关注', '用户权限', '', '2', '13');
INSERT INTO `permission`  VALUES ('14', '收藏', '用户权限', '', '2', '14');
INSERT INTO `permission`  VALUES ('15', '在线阅读', '用户权限', '', '2', '15');
INSERT INTO `permission`  VALUES ('16', '原文阅读', '用户权限', '', '2', '16');
INSERT INTO `permission`  VALUES (17, '年鉴管理', '资源管理', '/bookManager', '1', '17');
INSERT INTO `permission`  VALUES ('18','人物词条', '资源管理', '/personEntry', '1', '18');
INSERT INTO `permission`  VALUES ('19','论文词条', '资源管理', '/paperEntry',  '1', '19');
INSERT INTO `permission`  VALUES ('20','课题词条', '资源管理', '/topicEntry', '1', '20');
INSERT INTO `permission`  VALUES ('21','图书词条', '资源管理', '/bookEntry',  '1', '21');
INSERT INTO `permission`  VALUES ('22','会议词条', '资源管理', '/meetingEntry',  '1', '22');
INSERT INTO `permission`  VALUES ('23','机构词条', '资源管理', '/mechanismEntry',  '1', '23');
INSERT INTO `permission`  VALUES ('24','大事记词条', '资源管理', '/memorabiliayEntry',  '1', '24');
INSERT INTO `permission`  VALUES ('25','无权限', '用户权限', '',  '2', '25');

/*管理员基础账号*/
INSERT INTO `user` VALUES ('1', 'admin', '96e79218965eb72c92a549dd5a330112', '系统管理员', 'admin@qq.com', '18011112222', '社会科学出版社', '2017-05-11 18:00:51', '2017-06-15 13:12:07', '1', '0',NULL);

/*热词字段*/
INSERT INTO `hot_word`  VALUES ('8', '民俗学/神话主义/中国经济学年鉴/世界经济aaaaaaatt', '2017-09-01', '2017-09-01', '1');

-- ----------------------------
-- 节点数据
-- ----------------------------
INSERT INTO `node_data` VALUES ('1', '0', '基点', '1');

-- ----------------------------
--节点资源数据
-- ----------------------------
INSERT INTO `node_url` VALUES ('5000', '成功', '成功', '1');

-- ----------------------------
-- 默认角色数据
-- ----------------------------
INSERT INTO `role`  VALUES ('1', '系统管理员', '0', '1', '后台-管理员全系统', '1');
INSERT INTO `role`  VALUES ('2', '前台用户普通权限', '0', '2', '这是前台用户的普通权限，只包括关注，收藏，和拼接权限', '0');
INSERT INTO `role`  VALUES ('3', '前员用户超级权限', '0', '2', '这是前台用户的超级权限，包含前台用户的所有权限', '0');

-- ----------------------------
-- 权限映射角色数据
-- ----------------------------
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '1');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '2');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '3');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '4');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '6');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '8');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '10');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '11');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '12');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '13');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '14');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '15');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '16');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '17');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '18');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '19');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '20');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '21');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '22');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '23');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('1', '24');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('2', '12');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('2', '13');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('2', '14');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('3', '12');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('3', '13');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('3', '14');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('3', '15');
INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES ('3', '16');


-- book_details添加索引
ALTER TABLE `book_details`
  DROP INDEX `bookcode_index`,
  DROP INDEX `zid_index`,
  DROP INDEX `classify_index`,
  ADD INDEX `bookcode_index` (`bookcode`) USING BTREE,
  ADD INDEX `zid_index` (`zid`) USING BTREE,
  ADD INDEX `fid_index` (`fid`) USING BTREE,
  ADD INDEX `entry_index` (`entry`) USING BTREE,
  ADD INDEX `source_index` (`source`) USING BTREE,
  ADD INDEX `classify_index` (`classify`) USING BTREE;

ALTER TABLE `book`
  DROP INDEX `bookcode_index`,
  ADD INDEX `bookcode_index` (`bookcode`) USING BTREE;



