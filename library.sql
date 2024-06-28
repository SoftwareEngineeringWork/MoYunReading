/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 08/06/2024 22:30:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin1', '25f9e794323b453885f5181f1b624d0b', '13611117777', '202124100741@ynu.edu.cn', '2023-01-25 21:31:53', '2023-01-30 17:21:10', 1);
INSERT INTO `admin` VALUES (2, 'admin2', '25f9e794323b453885f5181f1b624d0b', '13066669999', '202124100801@ynu.edu.cn', '2023-01-26 14:13:55', '2023-01-26 15:02:43', 1);
INSERT INTO `admin` VALUES (3, 'admin3', '202cb962ac59075b964b07152d234b70', '13255557788', '202124100900@ynu.edu.cn', '2024-06-07 19:58:36', '2024-06-26 19:58:31', 1);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `publish_date` datetime NULL DEFAULT NULL COMMENT '出版日期',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `nums` int NULL DEFAULT NULL COMMENT '数量',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `book_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国际标准码',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面链接',
  `score` int NULL DEFAULT NULL COMMENT '借阅积分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `book_book_num_uindex`(`book_num` ASC) USING BTREE,
  INDEX `type`(`category` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '从你的全世界路过', '治愈', '2017-01-17 00:00:00', '张嘉佳', '郑州大学出版社', 10, '情感', 'A1234567890', '2023-01-28 16:40:38', '2023-01-28 19:50:33', 'https://hbimg.huabanimg.com/03eb601f332fd7cac9ced5cd0ad5b75462f89cc21eb57-pwoeML_fw658', 3);
INSERT INTO `book` VALUES (2, '散落星河的记忆', NULL, '2017-01-03 00:00:00', '桐华', '湖南文艺出版社', 16, '情感', 'A1234567891', '2023-01-28 17:35:30', '2023-01-30 14:45:26', 'https://p9.itc.cn/q_70/images03/20220705/2085b0c4c9f044168de227bd6e372c3b.jpeg', 6);
INSERT INTO `book` VALUES (8, '明朝那些事', NULL, '2019-06-18', '当年明月', '浙江人民出版社', 12, '历史 > 古代史', 'A1234567892', '2023-01-28 20:00:56', '2023-01-30 14:17:21', 'https://img2.baidu.com/it/u=1265710180,1493625572&fm=253&fmt=auto&app=138&f=JPEG?w=450&h=628', 4);
INSERT INTO `book` VALUES (9, '活着', NULL, '2016-08-22', '余华', '金盾出版社', 10, '情感', 'A1523665820', '2023-01-29 19:39:53', '2023-01-30 14:45:37', 'https://img2.baidu.com/it/u=2606115556,3100269997&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=666', 2);
INSERT INTO `book` VALUES (10, '三国演义', NULL, '2014-06-01', '罗贯中', '湖南教育出版社', 10, '历史 > 古代史', 'A1774457696', '2023-01-29 19:52:09', '2023-01-29 19:52:25', 'https://image31.bookschina.com/2011/20110618/5159183.jpg', 3);
INSERT INTO `book` VALUES (11, '西游记', NULL, '2012-09-12', '吴承恩', '现代出版社', 10, '神话', 'A1234569872', '2023-01-29 20:09:49', '2023-01-29 20:16:13', 'https://img2.baidu.com/it/u=1904244714,828637522&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=685', 5);
INSERT INTO `book` VALUES (12, '红楼梦', NULL, '2018-11-22', '曹雪芹', '现代出版社', 15, '历史 > 古代史', 'A3579585811', '2023-01-29 20:30:13', NULL, 'https://img0.baidu.com/it/u=3576585050,3527622018&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=667', 8);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '书名',
  `book_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图书标准码',
  `user_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户联系方式',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `score` int NULL DEFAULT NULL COMMENT '借书积分',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '已借出' COMMENT '借书状态',
  `days` int NULL DEFAULT 1 COMMENT '借书天数',
  `return_time` datetime NULL DEFAULT NULL COMMENT '归还日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `borrow_user_username_fk`(`user_id` ASC) USING BTREE,
  INDEX `borrow_book_book_num_fk`(`book_num` ASC) USING BTREE,
  CONSTRAINT `borrow_book_book_num_fk` FOREIGN KEY (`book_num`) REFERENCES `book` (`book_num`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `borrow_user_username_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (1, '从你的全世界路过', 'A1234567890', '20221990031500', '李华', '13066586531', '2024-01-29 17:41:46', NULL, 3, '已借出', 1, NULL);
INSERT INTO `borrow` VALUES (2, '明朝那些事', 'A1234567892', '20221990031500', '李华', '13066586531', '2024-01-29 17:42:09', '2024-01-29 17:42:39', 4, '已借出', 59, '2024-03-30 12:43:37');
INSERT INTO `borrow` VALUES (3, '散落星河的记忆', 'A1234567891', '20221109154946', '王五', '13066586531', '2023-06-29 17:45:57', '2023-10-29 18:05:58', 5, '已借出', 120, NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `pid` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '童话', '童话类图书', NULL, '2023-01-27 20:13:54', '2023-01-27 21:42:05');
INSERT INTO `category` VALUES (2, '情感', '', NULL, '2023-01-27 20:24:51', '2023-01-27 21:46:26');
INSERT INTO `category` VALUES (3, '历史', NULL, NULL, '2023-01-27 20:38:04', '2023-01-27 20:43:59');
INSERT INTO `category` VALUES (4, '儿童童话', '', '1', '2023-01-27 21:07:38', '2023-01-27 21:45:38');
INSERT INTO `category` VALUES (5, '程序员童话', '程序员', '1', '2023-01-27 21:08:49', '2023-01-28 17:48:09');
INSERT INTO `category` VALUES (6, '编程', '编程类图书', NULL, '2023-01-27 21:21:55', NULL);
INSERT INTO `category` VALUES (7, '文学', NULL, NULL, '2023-01-27 21:29:34', NULL);
INSERT INTO `category` VALUES (8, '现代文学', NULL, '7', '2023-01-27 21:35:03', NULL);
INSERT INTO `category` VALUES (9, '古代文学', NULL, '7', '2023-01-27 21:35:42', NULL);
INSERT INTO `category` VALUES (11, '现代史', NULL, '3', '2023-01-27 21:41:12', NULL);
INSERT INTO `category` VALUES (12, '古代史', NULL, '3', '2023-01-28 19:03:00', NULL);
INSERT INTO `category` VALUES (13, '神话', NULL, NULL, '2023-01-29 20:16:00', NULL);

-- ----------------------------
-- Table structure for circle
-- ----------------------------
DROP TABLE IF EXISTS `circle`;
CREATE TABLE `circle`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '书圈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of circle
-- ----------------------------
INSERT INTO `circle` VALUES (1, '肖雨', '大家最近在读什么书？求推荐。', '2024-05-08 13:00:00');
INSERT INTO `circle` VALUES (2, '夏明', '刚读完《活着》，非常震撼。', '2024-05-12 14:00:00');
INSERT INTO `circle` VALUES (3, '周琳', '《红楼梦》是我最喜欢的古典文学之一。', '2024-06-8 15:00:00');

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评价内容',
  `review_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '书评表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review
-- ----------------------------
INSERT INTO `review` VALUES (101, '张三', '这本书非常感人，让我度过了一个不眠之夜。', '2024-06-01 10:00:00');
INSERT INTO `review` VALUES (102, '李四', '故事情节跌宕起伏，推荐给喜欢历史题材的朋友。', '2024-06-08 11:00:00');
INSERT INTO `review` VALUES (103, '王五', '虽然书的封面设计一般，但内容确实精彩。', '2024-06-08 12:00:00');
INSERT INTO `review` VALUES (105, '肖雨', '哇，这本书真的超推荐！', '2024-05-16 16:00:00');
INSERT INTO `review` VALUES (106, '周琳', '好喜欢这本书，友友们还有类似的书推荐吗？', '2024-06-06 16:00:00');
INSERT INTO `review` VALUES (107, '李华', 'nice!', '2024-06-06 16:00:00');
INSERT INTO `review` VALUES (108, '夏明', '这本书真的很好看。', '2024-05-07 16:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住址',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `account` int NULL DEFAULT NULL COMMENT '账户余额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username` ASC) USING BTREE,
  UNIQUE INDEX `user_email_uindex`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (101, '张三', '20221378199836', 20, '男', '13066586531', '1111@qq.com', '辽宁省沈阳市', '2021-04-02 20:50:29', '2023-01-29 20:50:48', 38);
INSERT INTO `user` VALUES (102, '李四', '20221378199821', 19, '女', '13366589654', NULL, '云南昆明', '2022-05-09 20:50:29', NULL, 50);
INSERT INTO `user` VALUES (103, '王五', '20221109154946', 21, '男', '13066586531', '876606615@qq.com', '辽宁沈阳', '2022-10-09 20:50:29', NULL, 100);
INSERT INTO `user` VALUES (104, '赵六', '20221109240191', 11, '男', '13876523345', NULL, '云南昆明', '2022-11-18 20:50:29', NULL, 20);
INSERT INTO `user` VALUES (105, '肖雨', '20221109170612', 20, '男', '13955545545', NULL, '辽宁沈阳', '2023-05-22 20:53:04', '2023-11-09 23:44:28', 10);
INSERT INTO `user` VALUES (106, '周琳', '20221110211791', 18, '女', '17765318052', NULL, '河北邯郸', '2023-11-19 20:17:55', '2023-12-10 20:36:02', 10);
INSERT INTO `user` VALUES (107, '李华', '20221990031500', 36, '男', '13066586531', NULL, '辽宁沈阳', '2023-12-10 20:24:56', NULL, 5);
INSERT INTO `user` VALUES (108, '夏明', '20221679552521', 17, '女', '15892635574', NULL, '河南洛阳', '2024-02-20 20:32:22', NULL, 10);
INSERT INTO `user` VALUES (109, '杨肖', '20221378199815', 27, '女', '13066586531', NULL, '广西南宁', '2024-01-25 23:02:57', NULL, 10);

SET FOREIGN_KEY_CHECKS = 1;
