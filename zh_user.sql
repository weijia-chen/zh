/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.31.175
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : zh_user

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 07/06/2022 00:36:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_collect
-- ----------------------------
DROP TABLE IF EXISTS `tb_collect`;
CREATE TABLE `tb_collect`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `problem_id` bigint(100) NOT NULL COMMENT 'md5密码',
  PRIMARY KEY (`user_id`, `problem_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收藏问题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_collect
-- ----------------------------
INSERT INTO `tb_collect` VALUES (1, 1);
INSERT INTO `tb_collect` VALUES (1, 9);
INSERT INTO `tb_collect` VALUES (1, 18);
INSERT INTO `tb_collect` VALUES (1, 31);
INSERT INTO `tb_collect` VALUES (1, 33);
INSERT INTO `tb_collect` VALUES (1, 36);
INSERT INTO `tb_collect` VALUES (2, 1);
INSERT INTO `tb_collect` VALUES (3, 1);
INSERT INTO `tb_collect` VALUES (11, 30);
INSERT INTO `tb_collect` VALUES (11, 31);
INSERT INTO `tb_collect` VALUES (1521393915268251649, 31);

-- ----------------------------
-- Table structure for tb_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow`;
CREATE TABLE `tb_follow`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `target_id` bigint(20) NOT NULL COMMENT '被关注用户ID',
  `follow_time` datetime(0) NULL DEFAULT NULL COMMENT '关注的时间',
  PRIMARY KEY (`user_id`, `target_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_follow
-- ----------------------------
INSERT INTO `tb_follow` VALUES (1, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 2, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 3, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 4, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 5, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 11, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 12, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1, 1521393915268251600, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (2, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (2, 11, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (3, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (3, 11, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (4, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (4, 2, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (4, 3, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (4, 11, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (5, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (11, 11, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (11, 12, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (12, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1521393915268251649, 1, '2022-06-06 17:08:59');
INSERT INTO `tb_follow` VALUES (1521393915268251649, 11, '2022-06-06 17:08:59');

-- ----------------------------
-- Table structure for tb_follow_problem
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow_problem`;
CREATE TABLE `tb_follow_problem`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `problem_id` bigint(20) NOT NULL,
  `follow_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uid_pid`(`user_id`, `problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_follow_problem
-- ----------------------------
INSERT INTO `tb_follow_problem` VALUES (1, 1, 1, '2022-06-06 20:58:04');
INSERT INTO `tb_follow_problem` VALUES (2, 1, 2, '2022-06-06 20:58:11');
INSERT INTO `tb_follow_problem` VALUES (5, 1, 1234, '2022-06-06 21:16:33');
INSERT INTO `tb_follow_problem` VALUES (6, 1, 123, '2022-06-06 13:22:01');
INSERT INTO `tb_follow_problem` VALUES (10, 1, 35, NULL);

-- ----------------------------
-- Table structure for tb_follow_topic
-- ----------------------------
DROP TABLE IF EXISTS `tb_follow_topic`;
CREATE TABLE `tb_follow_topic`  (
  `user_id` bigint(20) NOT NULL,
  `topic_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_follow_topic
-- ----------------------------
INSERT INTO `tb_follow_topic` VALUES (1, 1);
INSERT INTO `tb_follow_topic` VALUES (1, 2);
INSERT INTO `tb_follow_topic` VALUES (1, 3);
INSERT INTO `tb_follow_topic` VALUES (1, 4);
INSERT INTO `tb_follow_topic` VALUES (1, 16);
INSERT INTO `tb_follow_topic` VALUES (2, 1);
INSERT INTO `tb_follow_topic` VALUES (3, 1);
INSERT INTO `tb_follow_topic` VALUES (4, 1);
INSERT INTO `tb_follow_topic` VALUES (5, 1);
INSERT INTO `tb_follow_topic` VALUES (11, 1);
INSERT INTO `tb_follow_topic` VALUES (11, 2);
INSERT INTO `tb_follow_topic` VALUES (12, 1);
INSERT INTO `tb_follow_topic` VALUES (12, 2);
INSERT INTO `tb_follow_topic` VALUES (12, 3);
INSERT INTO `tb_follow_topic` VALUES (12, 4);
INSERT INTO `tb_follow_topic` VALUES (12, 5);

-- ----------------------------
-- Table structure for tb_message_follow
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_follow`;
CREATE TABLE `tb_message_follow`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '接收消息的用户',
  `kind` int(3) NULL DEFAULT NULL COMMENT '消息类型--1：关注的人发布回答；2:关注的人提出问题',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '消息产生的时间',
  `from_user_id` int(11) NULL DEFAULT NULL COMMENT '消息的来源用户id',
  `from_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息的来源用户昵称',
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标话题',
  `problem_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题的标题，用于显示',
  `reply_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标回答',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_message_follow
-- ----------------------------
INSERT INTO `tb_message_follow` VALUES (1, 2, 1, '2022-05-01 11:44:05', 1, '432432', 27, 'g福岛事故的发生给广泛的', NULL);
INSERT INTO `tb_message_follow` VALUES (90, 2, 1, '2022-05-01 11:53:00', 1, '432432', 4, '功放电路广东佛山给对方广东佛山国防大厦', NULL);
INSERT INTO `tb_message_follow` VALUES (91, 3, 1, '2022-05-01 11:53:00', 1, '432432', 4, '功放电路广东佛山给对方广东佛山国防大厦', NULL);
INSERT INTO `tb_message_follow` VALUES (92, 4, 1, '2022-05-01 11:53:00', 1, '432432', 4, '功放电路广东佛山给对方广东佛山国防大厦', NULL);
INSERT INTO `tb_message_follow` VALUES (93, 5, 1, '2022-05-01 11:53:00', 1, '432432', 4, '功放电路广东佛山给对方广东佛山国防大厦', NULL);
INSERT INTO `tb_message_follow` VALUES (94, 1, 1, '2022-05-01 12:02:09', 11, '大人', 9, '怎么学习java', NULL);
INSERT INTO `tb_message_follow` VALUES (95, 2, 1, '2022-05-01 12:02:09', 11, '大人', 9, '怎么学习java', NULL);
INSERT INTO `tb_message_follow` VALUES (96, 3, 1, '2022-05-01 12:02:09', 11, '大人', 9, '怎么学习java', NULL);
INSERT INTO `tb_message_follow` VALUES (97, 4, 1, '2022-05-01 12:02:09', 11, '大人', 9, '怎么学习java', NULL);
INSERT INTO `tb_message_follow` VALUES (106, 1, 1, '2022-05-01 12:12:21', 11, '大人', 12, '学习学不会啊', NULL);
INSERT INTO `tb_message_follow` VALUES (107, 2, 1, '2022-05-01 12:12:21', 11, '大人', 12, '学习学不会啊', NULL);
INSERT INTO `tb_message_follow` VALUES (108, 3, 1, '2022-05-01 12:12:21', 11, '大人', 12, '学习学不会啊', NULL);
INSERT INTO `tb_message_follow` VALUES (109, 4, 1, '2022-05-01 12:12:21', 11, '大人', 12, '学习学不会啊', NULL);
INSERT INTO `tb_message_follow` VALUES (110, 1, 1, '2022-05-01 12:20:51', 11, '大人', 22, '规范结果回复华国锋', NULL);
INSERT INTO `tb_message_follow` VALUES (111, 2, 1, '2022-05-01 12:20:51', 11, '大人', 22, '规范结果回复华国锋', NULL);
INSERT INTO `tb_message_follow` VALUES (112, 3, 1, '2022-05-01 12:20:51', 11, '大人', 22, '规范结果回复华国锋', NULL);
INSERT INTO `tb_message_follow` VALUES (113, 4, 1, '2022-05-01 12:20:51', 11, '大人', 22, '规范结果回复华国锋', NULL);
INSERT INTO `tb_message_follow` VALUES (114, 1, 1, '2022-05-01 12:26:53', 11, '大人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_follow` VALUES (115, 2, 1, '2022-05-01 12:26:53', 11, '大人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_follow` VALUES (116, 3, 1, '2022-05-01 12:26:53', 11, '大人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_follow` VALUES (117, 4, 1, '2022-05-01 12:26:53', 11, '大人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_follow` VALUES (118, 2, 2, '2022-05-01 13:08:15', 1, '432432', 28, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (119, 3, 2, '2022-05-01 13:08:15', 1, '432432', 28, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (120, 4, 2, '2022-05-01 13:08:15', 1, '432432', 28, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (121, 5, 2, '2022-05-01 13:08:15', 1, '432432', 28, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (122, 12, 2, '2022-05-01 13:08:15', 1, '432432', 28, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (123, 2, 2, '2022-05-01 13:20:24', 1, '432432', 29, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (124, 3, 2, '2022-05-01 13:20:24', 1, '432432', 29, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (125, 4, 2, '2022-05-01 13:20:24', 1, '432432', 29, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (126, 5, 2, '2022-05-01 13:20:24', 1, '432432', 29, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (127, 12, 2, '2022-05-01 13:20:24', 1, '432432', 29, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (128, 2, 2, '2022-05-01 13:20:26', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (129, 3, 2, '2022-05-01 13:20:26', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (130, 4, 2, '2022-05-01 13:20:26', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (131, 5, 2, '2022-05-01 13:20:26', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (132, 12, 2, '2022-05-01 13:20:26', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (133, 2, 2, '2022-05-01 13:31:35', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (134, 3, 2, '2022-05-01 13:31:35', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (135, 4, 2, '2022-05-01 13:31:35', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (136, 5, 2, '2022-05-01 13:31:35', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (137, 12, 2, '2022-05-01 13:31:35', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (138, 1, 1, '2022-05-01 15:11:19', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (139, 2, 1, '2022-05-01 15:11:19', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (140, 3, 1, '2022-05-01 15:11:19', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (141, 4, 1, '2022-05-01 15:11:19', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (142, 1, 1, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (143, 2, 1, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (144, 3, 1, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (145, 4, 1, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (146, 11, 1, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (147, 2, 1, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (148, 3, 1, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (149, 4, 1, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (150, 5, 1, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (151, 12, 1, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_follow` VALUES (152, 2, 2, '2022-05-02 13:36:36', 1, '432432', 32, '克隆。结果被v客户机', NULL);
INSERT INTO `tb_message_follow` VALUES (153, 3, 2, '2022-05-02 13:36:36', 1, '432432', 32, '克隆。结果被v客户机', NULL);
INSERT INTO `tb_message_follow` VALUES (154, 4, 2, '2022-05-02 13:36:36', 1, '432432', 32, '克隆。结果被v客户机', NULL);
INSERT INTO `tb_message_follow` VALUES (155, 5, 2, '2022-05-02 13:36:36', 1, '432432', 32, '克隆。结果被v客户机', NULL);
INSERT INTO `tb_message_follow` VALUES (156, 12, 2, '2022-05-02 13:36:36', 1, '432432', 32, '克隆。结果被v客户机', NULL);
INSERT INTO `tb_message_follow` VALUES (157, 2, 2, '2022-05-02 13:36:59', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_follow` VALUES (158, 3, 2, '2022-05-02 13:36:59', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_follow` VALUES (159, 4, 2, '2022-05-02 13:36:59', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_follow` VALUES (160, 5, 2, '2022-05-02 13:36:59', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_follow` VALUES (161, 12, 2, '2022-05-02 13:36:59', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_follow` VALUES (162, 2, 2, '2022-05-03 06:52:08', 1, '432432', 34, 'f阿斯顿发射点', NULL);
INSERT INTO `tb_message_follow` VALUES (163, 3, 2, '2022-05-03 06:52:08', 1, '432432', 34, 'f阿斯顿发射点', NULL);
INSERT INTO `tb_message_follow` VALUES (164, 4, 2, '2022-05-03 06:52:08', 1, '432432', 34, 'f阿斯顿发射点', NULL);
INSERT INTO `tb_message_follow` VALUES (165, 5, 2, '2022-05-03 06:52:08', 1, '432432', 34, 'f阿斯顿发射点', NULL);
INSERT INTO `tb_message_follow` VALUES (166, 12, 2, '2022-05-03 06:52:08', 1, '432432', 34, 'f阿斯顿发射点', NULL);
INSERT INTO `tb_message_follow` VALUES (167, 2, 1, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (168, 3, 1, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (169, 4, 1, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (170, 5, 1, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (171, 12, 1, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_follow` VALUES (172, 1, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (173, 2, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (174, 3, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (175, 4, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (176, 5, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (177, 12, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (178, 1521393915268251649, 2, '2022-06-06 13:08:26', 1, '432432', 35, '反对广泛但是', NULL);
INSERT INTO `tb_message_follow` VALUES (179, 1, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (180, 2, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (181, 3, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (182, 4, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (183, 5, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (184, 12, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_follow` VALUES (185, 1521393915268251649, 2, '2022-06-06 13:09:04', 1, '432432', 36, '发士大夫士大夫十大', NULL);

-- ----------------------------
-- Table structure for tb_message_me
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_me`;
CREATE TABLE `tb_message_me`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '接收消息的用户',
  `kind` int(3) NULL DEFAULT NULL COMMENT '消息类型--1：回答被点赞；2:回答被评论 ;3：自己被关注；4：自己的问题有人回答；5:自己的问题被收藏了',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '消息产生的时间',
  `from_user_id` int(11) NULL DEFAULT NULL COMMENT '消息的来源用户id',
  `from_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息的来源用户昵称',
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标话题',
  `problem_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题的标题，用于显示',
  `reply_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标回答',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 144 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_message_me
-- ----------------------------
INSERT INTO `tb_message_me` VALUES (80, 1, 4, '2022-05-01 12:20:51', 11, '大人', 22, '规范结果回复华国锋', NULL);
INSERT INTO `tb_message_me` VALUES (81, 1, 4, '2022-05-01 12:26:53', 11, '大人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_me` VALUES (82, 1, 4, '2022-05-01 12:30:08', 12, '小人', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_me` VALUES (83, 1, 4, '2022-05-01 12:37:52', 12, '小人', 27, 'g福岛事故的发生给广泛的', NULL);
INSERT INTO `tb_message_me` VALUES (84, 1, 4, '2022-05-01 12:39:42', 12, '小人', 18, '的设计开发优惠的撒', NULL);
INSERT INTO `tb_message_me` VALUES (85, 1, 4, '2022-05-01 12:44:50', 12, '小人', 24, 'df的撒飞洒地方', NULL);
INSERT INTO `tb_message_me` VALUES (86, 1, 4, '2022-05-01 12:49:27', 12, '小人', 21, 'iu也会胡', NULL);
INSERT INTO `tb_message_me` VALUES (87, 1, 5, '2022-05-01 13:48:56', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (88, 1, 5, '2022-05-01 13:49:10', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_me` VALUES (89, 35, 1, '2022-05-01 14:32:40', 11, '大人', 26, '幅度萨芬士大夫撒旦', NULL);
INSERT INTO `tb_message_me` VALUES (90, 1, 1, '2022-05-01 14:37:11', 11, '大人', 24, 'df的撒飞洒地方', NULL);
INSERT INTO `tb_message_me` VALUES (91, 1, 1, '2022-05-01 14:57:43', 11, '大人', 24, 'df的撒飞洒地方', NULL);
INSERT INTO `tb_message_me` VALUES (92, 1, 2, '2022-05-01 14:59:56', 11, '大人', 24, 'df的撒飞洒地方', NULL);
INSERT INTO `tb_message_me` VALUES (93, 12, 3, '2022-05-01 15:10:41', 11, '大人', NULL, NULL, NULL);
INSERT INTO `tb_message_me` VALUES (94, 1, 4, '2022-05-01 15:11:19', 11, '大人', 30, '话题人', NULL);
INSERT INTO `tb_message_me` VALUES (95, 11, 3, '2022-05-01 15:11:22', 11, '大人', NULL, NULL, NULL);
INSERT INTO `tb_message_me` VALUES (96, 1, 4, '2022-05-01 15:11:51', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (97, 11, 1, '2022-05-01 15:11:56', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (98, 11, 2, '2022-05-01 15:11:57', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (99, 11, 2, '2022-05-01 15:11:58', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (100, 11, 2, '2022-05-01 15:12:00', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (101, 11, 2, '2022-05-01 15:12:02', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (102, 11, 2, '2022-05-01 15:12:04', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (103, 11, 2, '2022-05-01 15:12:05', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (104, 11, 2, '2022-05-01 15:12:07', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (105, 11, 2, '2022-05-01 15:12:08', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (106, 11, 2, '2022-05-01 15:12:09', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (107, 11, 2, '2022-05-01 15:12:10', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (108, 11, 2, '2022-05-01 15:12:10', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (109, 11, 2, '2022-05-01 15:12:11', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (110, 11, 2, '2022-05-01 15:12:11', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (111, 11, 2, '2022-05-01 15:12:11', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (112, 11, 2, '2022-05-01 15:13:16', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (113, 11, 2, '2022-05-01 15:13:18', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (114, 11, 2, '2022-05-01 15:13:19', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (115, 11, 2, '2022-05-01 15:13:20', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (116, 11, 2, '2022-05-01 15:13:21', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (117, 11, 2, '2022-05-01 15:13:23', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (118, 11, 2, '2022-05-01 15:13:24', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (119, 11, 2, '2022-05-01 15:13:26', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (120, 11, 2, '2022-05-01 15:14:14', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (121, 11, 2, '2022-05-01 15:14:15', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (122, 11, 2, '2022-05-01 15:14:17', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (123, 11, 2, '2022-05-01 15:14:18', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (124, 11, 2, '2022-05-01 15:14:20', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (125, 11, 2, '2022-05-01 15:14:21', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (126, 11, 2, '2022-05-01 15:14:22', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (127, 11, 2, '2022-05-01 15:14:23', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (128, 11, 2, '2022-05-01 15:14:24', 11, '大人', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (129, 11, 1, '2022-05-02 13:36:02', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (130, 1, 4, '2022-05-02 13:36:09', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (131, 1, 1, '2022-05-02 13:36:15', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (132, 1, 5, '2022-05-03 06:52:19', 1, '432432', 31, '用户及规划皮考拉海购', NULL);
INSERT INTO `tb_message_me` VALUES (133, 12, 1, '2022-05-03 06:52:29', 1, '432432', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_me` VALUES (134, 11, 1, '2022-05-03 06:52:31', 1, '432432', 20, '男孩子怎么变得更帅？', NULL);
INSERT INTO `tb_message_me` VALUES (135, 1, 5, '2022-05-03 06:59:13', 1, '432432', 33, '也很疼', NULL);
INSERT INTO `tb_message_me` VALUES (136, 12, 3, '2022-05-03 06:59:51', 1, '432432', NULL, NULL, NULL);
INSERT INTO `tb_message_me` VALUES (137, 12, 2, '2022-05-03 07:11:08', 1, '432432', 27, 'g福岛事故的发生给广泛的', NULL);
INSERT INTO `tb_message_me` VALUES (138, 12, 2, '2022-05-03 07:11:12', 1, '432432', 27, 'g福岛事故的发生给广泛的', NULL);
INSERT INTO `tb_message_me` VALUES (139, 1, 4, '2022-05-03 07:22:35', 1, '432432', 30, '话题人', NULL);
INSERT INTO `tb_message_me` VALUES (140, 1, 3, '2022-05-03 07:22:39', 1, '432432', NULL, NULL, NULL);
INSERT INTO `tb_message_me` VALUES (141, 1521393915268251600, 3, '2022-05-30 13:36:00', 1, '432432', NULL, NULL, NULL);
INSERT INTO `tb_message_me` VALUES (142, 1, 5, '2022-06-06 13:31:47', 1, '432432', 36, '发士大夫士大夫十大', NULL);
INSERT INTO `tb_message_me` VALUES (143, 1, 5, '2022-06-06 13:36:12', 1, '432432', 35, '反对广泛但是', NULL);

-- ----------------------------
-- Table structure for tb_message_problem
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_problem`;
CREATE TABLE `tb_message_problem`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '接收消息的用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '消息产生的时间',
  `from_user_id` int(11) NULL DEFAULT NULL COMMENT '消息的来源用户id',
  `from_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息的来源用户昵称',
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标问题',
  `problem_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题的标题，用于显示',
  `reply_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标回答',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_message_topic
-- ----------------------------
DROP TABLE IF EXISTS `tb_message_topic`;
CREATE TABLE `tb_message_topic`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '接收消息的用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '消息产生的时间',
  `from_user_id` int(11) NULL DEFAULT NULL COMMENT '消息的来源用户id',
  `from_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息的来源用户昵称',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '产生消息的话题',
  `topic_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生消息的话题名称',
  `problem_id` int(11) NULL DEFAULT NULL COMMENT '消息的目标话题',
  `problem_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题的标题，用于显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_message_topic
-- ----------------------------
INSERT INTO `tb_message_topic` VALUES (22, 1, '2022-05-01 13:20:24', 1, '432432', 4, '生活', 29, '话题人');
INSERT INTO `tb_message_topic` VALUES (23, 12, '2022-05-01 13:20:24', 1, '432432', 4, '生活', 29, '话题人');
INSERT INTO `tb_message_topic` VALUES (24, 1, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (25, 2, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (26, 3, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (27, 4, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (28, 5, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (29, 11, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (30, 12, '2022-05-01 13:20:26', 1, '432432', 1, 'java', 30, '话题人');
INSERT INTO `tb_message_topic` VALUES (31, 1, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (32, 2, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (33, 3, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (34, 4, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (35, 5, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (36, 11, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (37, 12, '2022-05-01 13:31:35', 1, '432432', 1, 'java', 31, '用户及规划皮考拉海购');
INSERT INTO `tb_message_topic` VALUES (38, 1, '2022-05-02 13:36:36', 1, '432432', 2, 'c++', 32, '克隆。结果被v客户机');
INSERT INTO `tb_message_topic` VALUES (39, 11, '2022-05-02 13:36:36', 1, '432432', 2, 'c++', 32, '克隆。结果被v客户机');
INSERT INTO `tb_message_topic` VALUES (40, 12, '2022-05-02 13:36:36', 1, '432432', 2, 'c++', 32, '克隆。结果被v客户机');
INSERT INTO `tb_message_topic` VALUES (41, 1, '2022-05-02 13:36:59', 1, '432432', 2, 'c++', 33, '也很疼');
INSERT INTO `tb_message_topic` VALUES (42, 11, '2022-05-02 13:36:59', 1, '432432', 2, 'c++', 33, '也很疼');
INSERT INTO `tb_message_topic` VALUES (43, 12, '2022-05-02 13:36:59', 1, '432432', 2, 'c++', 33, '也很疼');
INSERT INTO `tb_message_topic` VALUES (44, 1, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (45, 2, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (46, 3, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (47, 4, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (48, 5, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (49, 11, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (50, 12, '2022-06-06 13:08:26', 1, '432432', 1, 'java', 35, '反对广泛但是');
INSERT INTO `tb_message_topic` VALUES (51, 1, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (52, 2, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (53, 3, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (54, 4, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (55, 5, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (56, 11, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');
INSERT INTO `tb_message_topic` VALUES (57, 12, '2022-06-06 13:09:04', 1, '432432', 1, 'java', 36, '发士大夫士大夫十大');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'md5密码',
  `nick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'E-Mail',
  `fans_count` int(20) NULL DEFAULT 0 COMMENT '粉丝数',
  `follow_count` int(20) NULL DEFAULT 0 COMMENT '关注数',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码后的盐',
  `thumb_up` bigint(20) NULL DEFAULT 0 COMMENT '收获的赞',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '上一次修改用户信息的时间，每个星期才能修改一次',
  `follow_user_message` int(255) NOT NULL DEFAULT 0 COMMENT '未读关注的人动态数量',
  `follow_problem_message` int(255) NOT NULL DEFAULT 0 COMMENT '未读关注的问题动态数量',
  `message_count` int(255) NOT NULL DEFAULT 0 COMMENT '未读的通知数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '02bb312403d62e841f1af92ebeeb0cd2', '432432', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', '321@qq.com', 11, 7, '321ca68754144c679a757bfabf1e9cd7', 0, '2022-04-21 16:41:09', 0, 0, 0);
INSERT INTO `tb_user` VALUES (2, '6bf7f89a75c6a2f2d22acc27f55dcc27', 'fsadkj', '/res/zh/6a66b1bd5f7845ab981f9a9883d9e94a.jpeg', 'fda11si@qq.com', 0, 0, '9a936eaa27e8414d9635966b412bdd96', 0, NULL, 0, 0, 0);
INSERT INTO `tb_user` VALUES (3, 'ef85003a825961a69de1a110914c0a4e', 'uighjk', '/res/zh/d2ae338a71634d0bab7b685910aac3e3.jpeg', 'fda111si@qq.com', 0, 0, '61fc194e00bb4adea89586a614aaaf22', 0, NULL, 0, 0, 0);
INSERT INTO `tb_user` VALUES (4, '3d2f187603a6e8ffc786a0fc6ecd83aa', 'fedws', '/res/zh/31f8f0805f0d433ba90529a76c0846b8.jpeg', 'fdsa@qq.com', 0, 0, 'd1dc940875954b928fb35b7d2b704c33', 0, NULL, 0, 0, 0);
INSERT INTO `tb_user` VALUES (5, '81f44c193219d721eff33dfd2495719a', '123', '/res/zh/4b17eb6c4f934a6d931956c7cc7a6f04.jpeg', 'fds2a@qq.com', 0, 0, '0a3338550b11434295a83a880d287981', 0, NULL, 0, 0, 0);
INSERT INTO `tb_user` VALUES (6, 'c062f33288a08e3c7b3702a51a615d42', '123', '/res/zh/37e0b1a0b1b24085944f53156da3e844.jpeg', '123@qq.com', 0, 0, '94a245ba6a7e47a5b4a14a449f85f906', 0, '2022-04-19 15:46:03', 0, 0, 0);
INSERT INTO `tb_user` VALUES (7, '04b65ec1de14872d38c2afb61a814fc0', '123', '/res/zh/4f2be9da8aab4e6495d14458f441c989.jpeg', '121113@qq.com', 0, 0, 'd0b4c8b4fc134155ac026e3a732fbaf5', 0, '2022-04-20 07:19:23', 0, 0, 0);
INSERT INTO `tb_user` VALUES (8, '2ba4aa1c8cbda07e7d5e4d704f16f92b', '123', '/res/zh/86cecbf2dd734a59bad2af29d0e7a96d.jpeg', '1761807892@qq.com', 0, 0, 'abc62e2520c54a6e94281c7518335b7c', 0, '2022-04-20 12:05:09', 0, 0, 0);
INSERT INTO `tb_user` VALUES (9, '02bb312403d62e841f1af92ebeeb0cd2', '65497', '/res/zh/cb09f5816a31400ca949d12189e98099.jpeg', 'sentinel@qq.com', 0, 0, '321ca68754144c679a757bfabf1e9cd7', 0, '2022-04-20 13:40:52', 0, 0, 0);
INSERT INTO `tb_user` VALUES (10, '0632212b8a977b865a4a4463dc42eee9', '123', '/res/zh/d6a48b73d5ba46878d46f3c0a8ccc351.jpg', 'sentinel@163.com', 0, 0, 'ebededac2cdb4887b425e4bc21c281eb', 0, '2022-04-20 13:41:36', 0, 0, 0);
INSERT INTO `tb_user` VALUES (11, '0694d1c833196821572c87cda3e8dac7', '大人', '/res/zh/57d4be3854294aa8a2ef66850eb2baea.jpeg', '156@qq.com', 0, 2, 'b2281c7e94e641eda85f27254293c76e', 0, '2022-05-01 11:55:53', 0, 0, 0);
INSERT INTO `tb_user` VALUES (12, '6c5f9e20cb9a8ec0f4f351d6c29311cf', '小人', '/res/zh/c8a10aef24aa4bef8006addcb783dd56.jpg', '111@qq.com', 0, 1, '6c7314eccca84f0294815bb8223f6f5c', 0, '2022-05-01 12:29:15', 0, 0, 0);
INSERT INTO `tb_user` VALUES (1521393915268251649, '9f4a91d80fef30115da7c9632c11d668', '1432', '/res/zh/9db77b3f0ed54d7e902374501ea3b596.jpg', '555@qq.com', 0, 2, 'da5349db909f400bb642fccb19e2b694', 0, '2022-05-03 07:39:10', 0, 0, 0);

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
