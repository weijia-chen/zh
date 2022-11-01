/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.31.175
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : zh_qa

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 07/06/2022 00:36:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_collect`;
CREATE TABLE `tb_collect`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `favorites_id` bigint(20) NOT NULL COMMENT '收藏夹的id',
  `reply_id` bigint(20) NOT NULL COMMENT '回答的id',
  `collect_time` datetime(0) NOT NULL COMMENT '收藏的时间',
  `user_id` bigint(20) NOT NULL COMMENT '收藏该博客的用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid_rid`(`user_id`, `reply_id`) USING BTREE,
  INDEX `fid_time`(`favorites_id`, `collect_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1533849675375370242 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_collect
-- ----------------------------
INSERT INTO `tb_collect` VALUES (1533849675375370241, 1, 1, '2022-06-07 00:33:55', 1);

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '评论的的用户',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论用户的昵称',
  `user_avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论用户的头像',
  `reply_id` bigint(20) NOT NULL COMMENT '回答',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论的内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论的时间',
  `mood` int(1) NULL DEFAULT NULL COMMENT '评论的情绪',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reply_id_index`(`reply_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES (1, 1, 'fdg ', NULL, 1, '给福岛事故的发生广东佛山', '2022-04-25 22:42:21', NULL);
INSERT INTO `tb_comment` VALUES (2, 2, '广泛的', NULL, 1, '广泛的感到附属国的方式', '2022-04-25 22:42:35', NULL);
INSERT INTO `tb_comment` VALUES (3, 3, '古典风格', NULL, 1, '福岛事故的发生', '2022-04-25 22:42:47', NULL);
INSERT INTO `tb_comment` VALUES (4, 4, '感到附属国地方', NULL, 1, '风格的回复火箭股份空间', '2022-04-25 22:43:00', NULL);
INSERT INTO `tb_comment` VALUES (5, 1, 'fdg', NULL, 2, 'fgdhfggfh', '2022-04-25 22:43:12', NULL);
INSERT INTO `tb_comment` VALUES (6, 2, 'fas', NULL, 3, '和规范但是结果很快建立士大夫很快就', '2022-04-25 22:43:24', NULL);
INSERT INTO `tb_comment` VALUES (7, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 1, '国际化反抗类毒素就港口来打发时间离开过路口', '2022-04-26 23:10:17', NULL);
INSERT INTO `tb_comment` VALUES (8, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 6, '工行房贷回复', '2022-04-26 23:11:19', NULL);
INSERT INTO `tb_comment` VALUES (9, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 3, 'g范德萨给的fs', '2022-04-26 23:11:24', NULL);
INSERT INTO `tb_comment` VALUES (10, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 4, 'gdfsgjoidsfjgkldfsjlkglk;fsd ', '2022-04-26 23:17:46', NULL);
INSERT INTO `tb_comment` VALUES (11, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 5, 'gfdsgoidfsujgoikdfsjlgk;jldkfsjlkgj ds', '2022-04-26 23:17:51', NULL);
INSERT INTO `tb_comment` VALUES (12, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 6, '发生了打开激发了十大进了房间卢卡斯大家记录范德萨了看风景卢卡斯的骄傲', '2022-04-26 23:18:02', NULL);
INSERT INTO `tb_comment` VALUES (13, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 5, '刚刚', '2022-04-26 23:20:08', NULL);
INSERT INTO `tb_comment` VALUES (14, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 4, '浏览', '2022-04-26 23:20:12', NULL);
INSERT INTO `tb_comment` VALUES (15, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 20, '不知道啊', '2022-04-27 20:52:12', NULL);
INSERT INTO `tb_comment` VALUES (16, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 20, '你认为呢', '2022-04-27 20:52:17', NULL);
INSERT INTO `tb_comment` VALUES (17, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 20, '你懂不懂军事的啊', '2022-04-27 20:52:27', NULL);
INSERT INTO `tb_comment` VALUES (18, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 21, '。。。。。。', '2022-04-27 22:35:24', NULL);
INSERT INTO `tb_comment` VALUES (19, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 1, '法国德国', '2022-04-28 20:55:31', NULL);
INSERT INTO `tb_comment` VALUES (20, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 1, '的萨芬', '2022-04-28 21:06:27', NULL);
INSERT INTO `tb_comment` VALUES (21, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 33, 'fgrdsghfd fdg ', '2022-05-01 22:57:43', NULL);
INSERT INTO `tb_comment` VALUES (22, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 33, 'gf hjgfujghfjfgh', '2022-05-01 22:59:56', NULL);
INSERT INTO `tb_comment` VALUES (23, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gdfsgdfsgdfsgfds', '2022-05-01 23:11:57', NULL);
INSERT INTO `tb_comment` VALUES (24, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gsdfgdf', '2022-05-01 23:11:58', NULL);
INSERT INTO `tb_comment` VALUES (25, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gdfsg', '2022-05-01 23:12:00', NULL);
INSERT INTO `tb_comment` VALUES (26, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gfdsgdfs', '2022-05-01 23:12:02', NULL);
INSERT INTO `tb_comment` VALUES (27, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gfdsgdfs', '2022-05-01 23:12:04', NULL);
INSERT INTO `tb_comment` VALUES (28, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'gfdjghfj', '2022-05-01 23:12:05', NULL);
INSERT INTO `tb_comment` VALUES (29, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'kujyhgkuyhgjk', '2022-05-01 23:12:07', NULL);
INSERT INTO `tb_comment` VALUES (30, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'lhkjljkh', '2022-05-01 23:12:08', NULL);
INSERT INTO `tb_comment` VALUES (31, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'kjhl', '2022-05-01 23:12:09', NULL);
INSERT INTO `tb_comment` VALUES (32, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'jkh', '2022-05-01 23:12:10', NULL);
INSERT INTO `tb_comment` VALUES (33, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'jkh', '2022-05-01 23:12:10', NULL);
INSERT INTO `tb_comment` VALUES (34, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'ljkh', '2022-05-01 23:12:11', NULL);
INSERT INTO `tb_comment` VALUES (35, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'ljkh', '2022-05-01 23:12:11', NULL);
INSERT INTO `tb_comment` VALUES (36, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'l', '2022-05-01 23:12:11', NULL);
INSERT INTO `tb_comment` VALUES (37, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'ghfdhdfg', '2022-05-01 23:13:16', NULL);
INSERT INTO `tb_comment` VALUES (38, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfdghdfg', '2022-05-01 23:13:18', NULL);
INSERT INTO `tb_comment` VALUES (39, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hjytdrhjdgf', '2022-05-01 23:13:19', NULL);
INSERT INTO `tb_comment` VALUES (40, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'jhfgdh', '2022-05-01 23:13:20', NULL);
INSERT INTO `tb_comment` VALUES (41, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfdg', '2022-05-01 23:13:21', NULL);
INSERT INTO `tb_comment` VALUES (42, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hdfg', '2022-05-01 23:13:23', NULL);
INSERT INTO `tb_comment` VALUES (43, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfdgh', '2022-05-01 23:13:24', NULL);
INSERT INTO `tb_comment` VALUES (44, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'htfdghfgdh', '2022-05-01 23:13:26', NULL);
INSERT INTO `tb_comment` VALUES (45, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'dfghdfg', '2022-05-01 23:14:14', NULL);
INSERT INTO `tb_comment` VALUES (46, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfgd', '2022-05-01 23:14:15', NULL);
INSERT INTO `tb_comment` VALUES (47, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hgfdhdfg', '2022-05-01 23:14:17', NULL);
INSERT INTO `tb_comment` VALUES (48, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfgdhfgd', '2022-05-01 23:14:18', NULL);
INSERT INTO `tb_comment` VALUES (49, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hdfghfgdh', '2022-05-01 23:14:20', NULL);
INSERT INTO `tb_comment` VALUES (50, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hjdfgh', '2022-05-01 23:14:21', NULL);
INSERT INTO `tb_comment` VALUES (51, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfdghfgd', '2022-05-01 23:14:22', NULL);
INSERT INTO `tb_comment` VALUES (52, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'jfghd', '2022-05-01 23:14:23', NULL);
INSERT INTO `tb_comment` VALUES (53, 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 52, 'hfgdhfdg', '2022-05-01 23:14:24', NULL);
INSERT INTO `tb_comment` VALUES (54, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 47, 'g粉丝的尬的方式', '2022-05-03 15:11:08', NULL);
INSERT INTO `tb_comment` VALUES (55, 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 47, '感到附属国的非官方的是', '2022-05-03 15:11:12', NULL);

-- ----------------------------
-- Table structure for tb_favorites
-- ----------------------------
DROP TABLE IF EXISTS `tb_favorites`;
CREATE TABLE `tb_favorites`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `user_id` bigint(20) NOT NULL COMMENT '收藏夹的主人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏夹表，用户可以将回答收藏到自己创建的收藏夹中' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_like
-- ----------------------------
DROP TABLE IF EXISTS `tb_like`;
CREATE TABLE `tb_like`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `reply_id` bigint(20) NOT NULL COMMENT '回答ID',
  PRIMARY KEY (`user_id`, `reply_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '点赞记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_like
-- ----------------------------
INSERT INTO `tb_like` VALUES (1, 1);

-- ----------------------------
-- Table structure for tb_problem
-- ----------------------------
DROP TABLE IF EXISTS `tb_problem`;
CREATE TABLE `tb_problem`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `visits` bigint(20) NULL DEFAULT 0 COMMENT '浏览量',
  `follow` bigint(20) NULL DEFAULT 0 COMMENT '关注数',
  `reply` bigint(20) NULL DEFAULT 0 COMMENT '回复数',
  `deleted` int(1) NULL DEFAULT 0 COMMENT '逻辑删除: 0：没删除，1：删除',
  `condition` int(1) NULL DEFAULT 0 COMMENT '审核状态，0：未审核，1：通过，2：违规',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '所属话题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '问题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_problem
-- ----------------------------
INSERT INTO `tb_problem` VALUES (1, 'i偶的还是风口浪尖的数据分类就卡死的了饭卡里说的广泛大使馆对方是个对方是个对方是个对方是个国防大厦感到附属国的方式广东佛山广东佛山', '国防大厦给i的否是官方的空间', '2022-04-23 13:41:46', '2022-04-23 13:41:46', 1, '432432', 178, 0, 12, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (3, 'i偶的还是风口浪尖的数据分类就卡死的了饭卡里说的感到附属国对方是个地方撒给', '国防大厦给i的否是官方的空间', '2022-04-23 13:42:24', '2022-04-23 13:42:24', 1, '432432', 6, 0, 1, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (4, '功放电路广东佛山给对方广东佛山国防大厦', 'g单方事故的发生广东佛山', '2022-04-23 16:30:33', '2022-04-23 16:30:33', 1, '432432', 6, 0, 1, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (5, '功放电路国防大厦国防大厦告诉对方广东佛山广东佛山', 'g单方事故的发生广东佛山法国德国地方', '2022-04-23 16:35:21', '2022-04-23 16:35:21', 1, '432432', 6, 0, 1, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (6, '功放电路范德萨广东佛山根深蒂固若夫特恢复供电', 'g单方事故的发生广东佛山法国德国地方', '2022-04-23 16:43:35', '2022-04-23 16:43:35', 1, '432432', 8, 0, 1, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (7, '功放电路的风格恢复供电和梵蒂冈', 'g单方事故的发生广东佛山法国德国地方给v反对', '2022-04-23 16:45:21', '2022-04-23 16:45:21', 1, '432432', 46, 0, 1, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (8, '发hi噢色大将发生大就离开房间按时灯笼裤就发绿卡的撒就离开盛大积分离开的撒娇考虑附件是大陆附件卢卡斯大家离开房间是的就立刻', '金融风控管理的距离可达结果来看打发时间离开国家队方式离开国家队弗兰克是结果考虑对方手机两个快递放假里看过的风景克隆', '2022-04-25 19:56:31', '2022-04-25 19:56:33', 1, '发电公司', 0, 0, 0, 0, 0, NULL);
INSERT INTO `tb_problem` VALUES (9, '怎么学习java', '真的是学不会啊，一堆知识', '2022-04-26 23:21:26', '2022-04-26 23:21:26', 1, '432432', 27, 1, 2, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (10, '孵育过夜回复几个海军军官和可见光和价格？', 'i哭就好好看记录和客家话客家话客家话看', '2022-04-27 20:51:27', '2022-04-27 20:51:27', 1, '432432', 2, 0, 1, 0, 0, 7);
INSERT INTO `tb_problem` VALUES (11, 'v幸存者v幸存者v自行车v幸存者', 'v出现在v', '2022-04-27 22:05:49', '2022-04-27 22:05:49', 1, '432432', 3, 0, 1, 0, 0, 6);
INSERT INTO `tb_problem` VALUES (12, '学习学不会啊', 'f阿斯蒂芬撒旦', '2022-04-27 22:34:54', '2022-04-27 22:34:54', 1, '432432', 7, 0, 2, 0, 0, 2);
INSERT INTO `tb_problem` VALUES (18, '的设计开发优惠的撒', 'dfasdfsad', '2022-04-28 16:41:28', '2022-04-28 16:41:28', 1, '123', 41, 2, 3, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (20, '男孩子怎么变得更帅？', '如题', '2022-04-28 22:47:01', '2022-04-28 22:47:01', 1, '432432', 11, 0, 3, 0, 0, 5);
INSERT INTO `tb_problem` VALUES (21, 'iu也会胡', '', '2022-05-01 15:49:49', '2022-05-01 15:49:49', 1, '432432', 5, 0, 2, 0, 0, 6);
INSERT INTO `tb_problem` VALUES (22, '规范结果回复华国锋', '', '2022-05-01 15:54:24', '2022-05-01 15:54:24', 1, '432432', 3, 0, 2, 0, 0, 3);
INSERT INTO `tb_problem` VALUES (23, 'y还给他梵蒂冈', '', '2022-05-01 16:02:15', '2022-05-01 16:02:15', 1, '432432', 3, 0, 2, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (24, 'df的撒飞洒地方', '富士达', '2022-05-01 16:08:54', '2022-05-01 16:08:54', 1, '432432', 9, 0, 3, 0, 0, 2);
INSERT INTO `tb_problem` VALUES (25, '发撒旦发生大', 'f士大夫的撒', '2022-05-01 19:03:24', '2022-05-01 19:03:24', 1, '432432', 2, 0, 1, 0, 0, 7);
INSERT INTO `tb_problem` VALUES (26, '幅度萨芬士大夫撒旦', '富士达富士达', '2022-05-01 19:05:16', '2022-05-01 19:05:16', 1, '432432', 2, 0, 1, 0, 0, 3);
INSERT INTO `tb_problem` VALUES (27, 'g福岛事故的发生给广泛的', '广泛的和法国', '2022-05-01 19:42:44', '2022-05-01 19:42:44', 1, '432432', 12, 0, 3, 0, 0, 2);
INSERT INTO `tb_problem` VALUES (28, '话题人', '广泛的', '2022-05-01 21:08:15', '2022-05-01 21:08:15', 1, '432432', 0, 0, 0, 0, 0, 3);
INSERT INTO `tb_problem` VALUES (29, '话题人', '广泛的', '2022-05-01 21:20:24', '2022-05-01 21:20:24', 1, '432432', 0, 0, 0, 0, 0, 4);
INSERT INTO `tb_problem` VALUES (30, '话题人', '广泛的', '2022-05-01 21:20:26', '2022-05-01 21:20:26', 1, '432432', 6, 1, 2, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (31, '用户及规划皮考拉海购', '广泛的', '2022-05-01 21:31:35', '2022-05-01 21:31:35', 1, '432432', 27, 3, 3, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (32, '克隆。结果被v客户机', '尽快备货', '2022-05-02 21:36:36', '2022-05-02 21:36:36', 1, '432432', 1, 0, 0, 0, 0, 2);
INSERT INTO `tb_problem` VALUES (33, '也很疼', 'g发达', '2022-05-02 21:36:59', '2022-05-02 21:36:59', 1, '432432', 2, 1, 0, 0, 0, 2);
INSERT INTO `tb_problem` VALUES (34, 'f阿斯顿发射点', '幅度萨芬手动', '2022-05-03 14:52:08', '2022-05-03 14:52:08', 1, '432432', 0, 0, 0, 0, 0, 6);
INSERT INTO `tb_problem` VALUES (35, '反对广泛但是', '', '2022-06-06 21:08:26', '2022-06-06 21:08:26', 1, '432432', 2, 1, 0, 0, 0, 1);
INSERT INTO `tb_problem` VALUES (36, '发士大夫士大夫十大', 'f士大夫十大', '2022-06-06 21:09:04', '2022-06-06 21:09:04', 1, '432432', 2, 1, 0, 0, 0, 1);

-- ----------------------------
-- Table structure for tb_reply
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `problem_id` bigint(20) NOT NULL COMMENT '问题ID',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回答内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `user_id` bigint(20) NOT NULL COMMENT '回答人ID',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回答人昵称',
  `user_avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回答人头像',
  `thumb_up` bigint(20) NULL DEFAULT 0 COMMENT '点赞数',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除: 0：没删除，1：删除',
  `condition` int(1) NULL DEFAULT 0 COMMENT '审核状态，0：未审核，1：通过，2：违规',
  `issue` int(1) NOT NULL DEFAULT 1 COMMENT '0:草稿;1:已发布',
  `collect` bigint(255) NOT NULL DEFAULT 0 COMMENT '收藏数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`, `user_id`) USING BTREE COMMENT '一个用户在一个问题下只能回答一次'
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回答' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_reply
-- ----------------------------
INSERT INTO `tb_reply` VALUES (1, 1, '告诉对方敢死队风格地方撒告诉对方', '2022-04-16 19:05:29', '2022-04-24 19:05:32', 1, '告诉对方', NULL, 120, 0, 0, 1, 3);
INSERT INTO `tb_reply` VALUES (3, 1, '反对是告诉对方', '2022-04-24 19:06:59', '2022-04-24 19:07:01', 2, '富士达', NULL, 8, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (4, 1, '规划加入天涯和法国', '2022-04-24 19:07:10', '2022-04-24 19:07:12', 3, '工行房贷三个', NULL, 7, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (5, 1, '感到附属国的方式', '2022-04-24 19:07:22', '2022-04-24 19:07:24', 4, '给范德萨给地方', NULL, 4, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (6, 1, '高富帅的人感染途径try法定规划iu耶稣的复活吧看见四大银行放假咯但是空间会发生的空间了因霍夫卡升级的话付款就old撒谎克拉斯放大镜来看的萨芬就立刻集散地立刻家里的款式风格就立刻十大附件立刻赶到附件是两块结果的方式离开寄过来的富士康就来扩大覆盖就了', '2022-04-24 19:07:39', '2022-04-24 19:07:40', 5, '公司梵蒂冈地方s', NULL, 20, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (7, 9, '风格士大夫但是范德萨范德萨富士达富士达范德萨', '2022-04-27 18:14:18', '2022-04-27 18:14:18', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (14, 7, '和官方机构恢复肌肤关乎价格和飞机飞过和', '2022-04-27 18:38:36', '2022-04-27 18:38:36', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (19, 6, '这个没有回答，可以回显吗', '2022-04-27 18:43:16', '2022-04-27 18:43:16', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (20, 10, '这是啥啊？', '2022-04-27 20:52:01', '2022-04-27 20:52:01', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (21, 12, '你学不会，我也学不会啊', '2022-04-27 22:35:16', '2022-04-27 22:35:16', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (26, 20, '我也不知道啊\n', '2022-04-28 22:47:11', '2022-04-28 22:47:11', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (28, 5, '靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠', '2022-05-01 15:38:55', '2022-05-01 15:38:55', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (29, 11, 'u有好几个好几个健康科技', '2022-05-01 15:50:11', '2022-05-01 15:50:11', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (30, 21, '与健康同归于尽个国家', '2022-05-01 15:52:33', '2022-05-01 15:52:33', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (31, 22, 'g犯得上广泛但是', '2022-05-01 15:54:37', '2022-05-01 15:54:37', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (32, 23, '很过分的话', '2022-05-01 16:02:25', '2022-05-01 16:02:25', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (33, 24, 'f的撒飞洒地方是', '2022-05-01 16:09:09', '2022-05-01 16:09:09', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (34, 25, 'f多少', '2022-05-01 19:03:38', '2022-05-01 19:03:38', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (35, 26, '大师傅大师傅似的国防大厦', '2022-05-01 19:05:25', '2022-05-01 19:05:25', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (36, 27, 'g分电视广播房管局更好 ', '2022-05-01 19:44:05', '2022-05-01 19:44:05', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (37, 4, 'jgfhjghfjfghd看见黄瓜', '2022-05-01 19:53:00', '2022-05-01 19:53:00', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (38, 23, '个人负担和华国锋', '2022-05-01 19:56:12', '2022-05-01 19:56:12', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (39, 18, 'g福岛事故的发生', '2022-05-01 19:59:47', '2022-05-01 19:59:47', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (40, 9, '12rergf ', '2022-05-01 20:02:09', '2022-05-01 20:02:09', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (41, 27, 'tyfuih', '2022-05-01 20:05:21', '2022-05-01 20:05:21', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (42, 24, 'yugfuikygiu ', '2022-05-01 20:11:18', '2022-05-01 20:11:18', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (43, 12, 'yuguihi', '2022-05-01 20:12:21', '2022-05-01 20:12:21', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (44, 22, 'gfsdgsdf ', '2022-05-01 20:20:51', '2022-05-01 20:20:51', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (45, 20, 'bhfdhfgh ', '2022-05-01 20:26:53', '2022-05-01 20:26:53', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (46, 20, 'gfdshfgjhgf', '2022-05-01 20:30:08', '2022-05-01 20:30:08', 12, '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (47, 27, 'jhgkghjkhjg ', '2022-05-01 20:37:52', '2022-05-01 20:37:52', 12, '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (48, 18, 'uhjgfdfghfgd', '2022-05-01 20:39:42', '2022-05-01 20:39:42', 12, '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (49, 24, 'gdfsgdfsgdfs', '2022-05-01 20:44:50', '2022-05-01 20:44:50', 12, '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (50, 21, 'fasdfsadfsda', '2022-05-01 20:49:27', '2022-05-01 20:49:27', 12, '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (51, 30, 'gfsdghfs', '2022-05-01 23:11:19', '2022-05-01 23:11:19', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (52, 31, 'fdsgsdf', '2022-05-01 23:11:51', '2022-05-01 23:11:51', 11, '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', 3, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (53, 31, 'iu两个月后故意开发故意可贵', '2022-05-02 21:36:09', '2022-05-02 21:36:09', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (54, 30, ' 你', '2022-05-03 15:22:35', '2022-05-03 15:22:35', 1, '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 0, 0, 0, 1, 0);
INSERT INTO `tb_reply` VALUES (55, 31, 'fdsafsd ', '2022-05-03 15:45:36', '2022-05-03 15:45:36', 1521393915268251649, '1432', '/res/zh/9db77b3f0ed54d7e902374501ea3b596.jpg', 0, 0, 0, 1, 0);

-- ----------------------------
-- Table structure for tb_topic
-- ----------------------------
DROP TABLE IF EXISTS `tb_topic`;
CREATE TABLE `tb_topic`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '话题iD',
  `topic_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '话题名称',
  `introduce` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_topic
-- ----------------------------
INSERT INTO `tb_topic` VALUES (1, 'java', 'java是一款优秀的编程语言，大家可以在这里讨论，一起解决bug');
INSERT INTO `tb_topic` VALUES (2, 'c++', 'C++是一种计算机高级程序设计语言，由C语言扩展升级而产生 [17]  ，最早于1979年由本贾尼·斯特劳斯特卢普在AT&T贝尔工作室研发');
INSERT INTO `tb_topic` VALUES (3, '医疗', '中华医史几千年，而这个字眼是在近几十年才出现，其实这是为了与国际接轨而新生的字眼，之前大多使用治疗。然而医疗也包含保健内容。');
INSERT INTO `tb_topic` VALUES (4, '生活', '每个人都会询问这个问题：“生活的意义是什么？”在生活中遭受不幸的人会觉得人生没有意义，而即便那些成功者也时常会迷茫于人生的意义是什么？我们要探讨的不是价值观是什么，而是生命的终极意义是什么。');
INSERT INTO `tb_topic` VALUES (5, '美妆', NULL);
INSERT INTO `tb_topic` VALUES (6, '政治', '政治（Politics）是指政府、政党等治理国家的行为。政治是以经济为基础的上层建筑，是经济的集中表现，是以国家权力为核心展开的各种社会活动和社会关系的总和。政治是牵动社会全体成员的利益并支配其行为的社会力量。');
INSERT INTO `tb_topic` VALUES (7, '军事', '军事（Military） [1]  ，即军队事务，古称军务，是与一个国家及政权的国防之武装力量有关的学问及事务。有人认为，军事为政治的一部分，但在中国古代，军、政是分开的。比较正式的说法为，军事是一种政治延续。');
INSERT INTO `tb_topic` VALUES (8, '经济', '简单地说，经济就是人们生产、流通、分配、消费一切物质精神资料的总称。这一概念微观指一个家庭的财产管理，宏观指一个国家的国民经济。在这一动态整体中，生产是基础，消费是终点。编程是编定程序的中文简称，就是让计算机代码解决某个问题，对某个计算体系规定一定的运算方式，使计算体系按照该计算方式运行，并最终得到相应结果的过程。');
INSERT INTO `tb_topic` VALUES (9, '编程', '编程是编定程序的中文简称，就是让计算机代码解决某个问题，对某个计算体系规定一定的运算方式，使计算体系按照该计算方式运行，并最终得到相应结果的过程。');
INSERT INTO `tb_topic` VALUES (10, '前端', '前端即网站前台部分，运行在PC端，移动端等浏览器上展现给用户浏览的网页。随着互联网技术的发展，HTML5，CSS3，前端框架的应用，跨平台响应式网页设计能够适应各种屏幕分辨率，合适的动效设计，给用户带来极高的用户体验。');
INSERT INTO `tb_topic` VALUES (11, 'spring', 'Spring框架是一个开放源代码的J2EE应用程序框架，由Rod Johnson发起，是针对bean的生命周期进行管理的轻量级容器（lightweight container）');
INSERT INTO `tb_topic` VALUES (12, '美女', '美女，汉语词语，拼音是měi nǚ，意思是容貌姣好、仪态优雅的女子。 [1] ');
INSERT INTO `tb_topic` VALUES (13, '情感', '情感是态度这一整体中的一部分，它与态度中的内向感受、意向具有协调一致性，是态度在生理上一种较复杂而又稳定的生理评价和体验。情感包括道德感和价值感两个方面，具体表现为爱情、幸福、仇恨、厌恶、美感等。 [1] ');
INSERT INTO `tb_topic` VALUES (14, '历史', '历史，简称“史”，指对人类社会过去的事件和活动，以及对这些事件行为有系统的记录、研究和诠释。历史是客观存在的，无论文学家们如何书写历史，历史都以自己的方式存在，不可改变。');
INSERT INTO `tb_topic` VALUES (15, '生物', '生物（Organism），是指具有动能的生命体，也是一个物体的集合。而个体生物指的是生物体，与非生物相对。 其元素包括：');
INSERT INTO `tb_topic` VALUES (16, '化学', '化学（chemistry）是自然科学的一种。化学是主要在分子、原子层面，研究物质的组成、性质、结构与变化规律的科学。');
INSERT INTO `tb_topic` VALUES (17, '物理', '物理学（physics）是研究物质最一般的运动规律和物质基本结构的学科。作为自然科学的带头学科，物理学研究大至宇宙，小至基本粒子等一切物质最基本的运动形式和规律，因此成为其他各自然科学学科的研究基础。');
INSERT INTO `tb_topic` VALUES (18, '数学', '数学[英语：mathematics，源自古希腊语μθημα（máthēma）；经常被缩写为math或maths]，是研究数量、结构、变化、空间以及信息等概念的一门学科。');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
