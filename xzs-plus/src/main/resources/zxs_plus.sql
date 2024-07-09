/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 8.0.30 : Database - sa_xzs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sa_xzs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sa_xzs`;

/*Table structure for table `t_exam_paper` */

DROP TABLE IF EXISTS `t_exam_paper`;

CREATE TABLE `t_exam_paper` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL,
  `paper_type` int DEFAULT NULL,
  `grade_level` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  `question_count` int DEFAULT NULL,
  `suggest_time` int DEFAULT NULL,
  `limit_start_time` timestamp(6) NULL DEFAULT NULL,
  `limit_end_time` timestamp(6) NULL DEFAULT NULL,
  `frame_text_content_id` bigint DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `task_exam_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_exam_paper` */

insert  into `t_exam_paper`(`id`,`name`,`subject_id`,`paper_type`,`grade_level`,`score`,`question_count`,`suggest_time`,`limit_start_time`,`limit_end_time`,`frame_text_content_id`,`create_user`,`create_time`,`deleted`,`task_exam_id`) values 
(1810228272383004672,'体术地基12',1810189750225735680,1,7,150,2,15,NULL,NULL,1810228272307507200,1,'2024-07-08 16:23:47.181000',0,NULL),
(1810478773339033600,'初一任务123',1810189750225735680,6,7,150,2,123,NULL,NULL,1810478773213204480,1,'2024-07-09 08:59:11.244000',0,1810478857210576898),
(1810554490588368896,'考试再范围',1810192319585062912,4,7,20,1,20,'2024-07-01 00:00:00.000000','2024-08-22 00:00:00.000000',1810554490546425856,1810229667467235328,'2024-07-09 14:00:03.665000',0,NULL),
(1810554665432125440,'过期考试',1810192319585062912,4,7,20,1,30,'2024-07-01 00:00:00.000000','2024-07-03 00:00:00.000000',1810554665377599488,1810229667467235328,'2024-07-09 14:00:45.360000',0,NULL),
(1810554828196286464,'考试未到',1810189750225735680,4,7,50,1,18,'2025-02-14 00:00:00.000000','2025-02-28 00:00:00.000000',1810554828162732032,1810229667467235328,'2024-07-09 14:01:24.172000',0,NULL);

/*Table structure for table `t_exam_paper_answer` */

DROP TABLE IF EXISTS `t_exam_paper_answer`;

CREATE TABLE `t_exam_paper_answer` (
  `id` bigint NOT NULL,
  `exam_paper_id` bigint DEFAULT NULL,
  `paper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `paper_type` int DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL,
  `system_score` int DEFAULT NULL,
  `user_score` int DEFAULT NULL,
  `paper_score` int DEFAULT NULL,
  `question_correct` int DEFAULT NULL,
  `question_count` int DEFAULT NULL,
  `do_time` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `task_exam_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_exam_paper_answer` */

insert  into `t_exam_paper_answer`(`id`,`exam_paper_id`,`paper_name`,`paper_type`,`subject_id`,`system_score`,`user_score`,`paper_score`,`question_correct`,`question_count`,`do_time`,`status`,`create_user`,`create_time`,`task_exam_id`) values 
(1810515755400478722,1810478773339033600,'初一任务123',6,1810189750225735680,50,50,150,1,2,7,2,1810229667467235328,'2024-07-09 11:26:08.458000',1810478857210576898),
(1810549159483445250,1810228272383004672,'体术地基12',1,1810189750225735680,0,0,150,0,2,9,2,1810229667467235328,'2024-07-09 13:38:52.586000',NULL),
(1810549276630355969,1810228272383004672,'体术地基12',1,1810189750225735680,0,0,150,0,2,3,2,1810229667467235328,'2024-07-09 13:39:20.541000',NULL),
(1810558332350803969,1810228272383004672,'体术地基12',1,1810189750225735680,100,100,150,1,2,2,2,1810229667467235328,'2024-07-09 14:15:19.432000',NULL),
(1810603789630312450,1810228272383004672,'体术地基12',1,1810189750225735680,0,0,150,0,2,4,2,1810229667467235328,'2024-07-09 17:15:57.428000',NULL);

/*Table structure for table `t_exam_paper_question_customer_answer` */

DROP TABLE IF EXISTS `t_exam_paper_question_customer_answer`;

CREATE TABLE `t_exam_paper_question_customer_answer` (
  `id` bigint NOT NULL,
  `question_id` bigint DEFAULT NULL,
  `exam_paper_id` bigint DEFAULT NULL,
  `exam_paper_answer_id` bigint DEFAULT NULL,
  `question_type` int DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL,
  `customer_score` int DEFAULT NULL,
  `question_score` int DEFAULT NULL,
  `question_text_content_id` bigint DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `text_content_id` bigint DEFAULT NULL,
  `do_right` tinyint(1) DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `item_order` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_exam_paper_question_customer_answer` */

insert  into `t_exam_paper_question_customer_answer`(`id`,`question_id`,`exam_paper_id`,`exam_paper_answer_id`,`question_type`,`subject_id`,`customer_score`,`question_score`,`question_text_content_id`,`answer`,`text_content_id`,`do_right`,`create_user`,`create_time`,`item_order`) values 
(1810515755467587586,1810215785824845825,1810478773339033600,1810515755400478722,2,1810189750225735680,50,50,1810215785770319873,'A,B',NULL,1,1810229667467235328,'2024-07-09 11:26:08.458000',1),
(1810515755467587587,1810215047916707842,1810478773339033600,1810515755400478722,1,1810189750225735680,0,100,1810215047849598977,'C',NULL,0,1810229667467235328,'2024-07-09 11:26:08.458000',2),
(1810549159516999682,1810215047916707842,1810228272383004672,1810549159483445250,1,1810189750225735680,0,100,1810215047849598977,'C',NULL,0,1810229667467235328,'2024-07-09 13:38:52.586000',1),
(1810549159579914242,1810215785824845825,1810228272383004672,1810549159483445250,2,1810189750225735680,0,50,1810215785770319873,'B,C',NULL,0,1810229667467235328,'2024-07-09 13:38:52.586000',2),
(1810549276697464834,1810215047916707842,1810228272383004672,1810549276630355969,1,1810189750225735680,0,100,1810215047849598977,'D',NULL,0,1810229667467235328,'2024-07-09 13:39:20.541000',1),
(1810549276697464835,1810215785824845825,1810228272383004672,1810549276630355969,2,1810189750225735680,0,50,1810215785770319873,'D',NULL,0,1810229667467235328,'2024-07-09 13:39:20.541000',2),
(1810558332531159042,1810215047916707842,1810228272383004672,1810558332350803969,1,1810189750225735680,100,100,1810215047849598977,'A',NULL,1,1810229667467235328,'2024-07-09 14:15:19.432000',1),
(1810558332531159043,1810215785824845825,1810228272383004672,1810558332350803969,2,1810189750225735680,0,50,1810215785770319873,'A',NULL,0,1810229667467235328,'2024-07-09 14:15:19.432000',2),
(1810603789676449794,1810215047916707842,1810228272383004672,1810603789630312450,1,1810189750225735680,0,100,1810215047849598977,'B',NULL,0,1810229667467235328,'2024-07-09 17:15:57.428000',1),
(1810603789739364353,1810215785824845825,1810228272383004672,1810603789630312450,2,1810189750225735680,0,50,1810215785770319873,'A',NULL,0,1810229667467235328,'2024-07-09 17:15:57.428000',2);

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` bigint NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `send_user_id` bigint DEFAULT NULL,
  `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `send_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `read_count` int DEFAULT NULL,
  `receive_user_count` int DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_message` */

insert  into `t_message`(`id`,`title`,`content`,`send_user_id`,`send_user_name`,`send_real_name`,`read_count`,`receive_user_count`,`create_time`) values 
(1810486549350313985,'发个信息测试下','1110000000',1,'admin','卡卡西',1,1,'2024-07-09 09:30:05.196000');

/*Table structure for table `t_message_user` */

DROP TABLE IF EXISTS `t_message_user`;

CREATE TABLE `t_message_user` (
  `id` bigint NOT NULL,
  `message_id` bigint DEFAULT NULL,
  `receive_user_id` bigint DEFAULT NULL,
  `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `receive_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `readed` tinyint(1) DEFAULT NULL,
  `read_time` timestamp(6) NULL DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_message_user` */

insert  into `t_message_user`(`id`,`message_id`,`receive_user_id`,`receive_user_name`,`receive_real_name`,`readed`,`read_time`,`create_time`) values 
(1810486549476143105,1810486549350313985,1810229667467235328,'s1','小李',1,'2024-07-09 14:58:42.991000','2024-07-09 09:30:05.196000');

/*Table structure for table `t_power` */

DROP TABLE IF EXISTS `t_power`;

CREATE TABLE `t_power` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `power_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限路径',
  `power_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限标识，数据权限例ExtLeaveMapper.selectPage',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `enable` tinyint NOT NULL COMMENT '状态(0:禁用,1:启用)',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_power` */

insert  into `t_power`(`id`,`menu_id`,`power_url`,`power_code`,`remark`,`enable`,`sort`,`create_time`) values 
(1,1,'/onlineUsers','online:look','获取在线用户信息',1,1,'2024-07-05 17:03:39');

/*Table structure for table `t_question` */

DROP TABLE IF EXISTS `t_question`;

CREATE TABLE `t_question` (
  `id` bigint NOT NULL,
  `question_type` int DEFAULT NULL,
  `subject_id` bigint DEFAULT NULL,
  `score` int DEFAULT NULL,
  `grade_level` int DEFAULT NULL,
  `difficult` int DEFAULT NULL,
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `info_text_content_id` bigint DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_question` */

insert  into `t_question`(`id`,`question_type`,`subject_id`,`score`,`grade_level`,`difficult`,`correct`,`info_text_content_id`,`create_user`,`status`,`create_time`,`deleted`) values 
(1810215047916707842,1,1810189750225735680,100,7,3,'A',1810215047849598977,1,1,'2024-07-08 15:31:14.195000',0),
(1810215785824845825,2,1810189750225735680,50,7,4,'A,B',1810215785770319873,1,1,'2024-07-08 15:34:10.142000',0),
(1810229444735176706,3,1810192319585062912,20,7,2,'对',1810229444659679233,1,1,'2024-07-08 16:28:26.674000',0);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_role` */

insert  into `t_role`(`role_id`,`role_name`,`role_key`,`create_time`,`update_time`,`del_flag`) values 
(2,'管理员','admin','2024-07-05 14:04:33','2024-07-05 14:04:36',0),
(3,'教师','teacher','2024-07-05 14:05:01','2024-07-05 14:05:04',0),
(4,'学生','student','2024-07-05 14:05:25','2024-07-05 14:05:27',0);

/*Table structure for table `t_role_power` */

DROP TABLE IF EXISTS `t_role_power`;

CREATE TABLE `t_role_power` (
  `role_id` bigint DEFAULT NULL,
  `power_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_role_power` */

/*Table structure for table `t_subject` */

DROP TABLE IF EXISTS `t_subject`;

CREATE TABLE `t_subject` (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int DEFAULT NULL,
  `level_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `item_order` int DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_subject` */

insert  into `t_subject`(`id`,`name`,`level`,`level_name`,`item_order`,`deleted`) values 
(1810189750225735680,'体术',7,'初一',NULL,0),
(1810192319585062912,'幻术',7,'初一',NULL,0),
(1810229771423059968,'忍术',7,'初一',NULL,0),
(1810229834467643392,'仙术',12,'高三',NULL,0);

/*Table structure for table `t_task_exam` */

DROP TABLE IF EXISTS `t_task_exam`;

CREATE TABLE `t_task_exam` (
  `id` bigint NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade_level` int DEFAULT NULL,
  `frame_text_content_id` bigint DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_task_exam` */

insert  into `t_task_exam`(`id`,`title`,`grade_level`,`frame_text_content_id`,`create_user`,`create_user_name`,`create_time`,`deleted`) values 
(1810466746287804417,'初一测试',7,1810466746191335426,1,'admin','2024-07-09 08:11:23.752000',1),
(1810477595031269377,'任务测试12',7,1810477594930606081,1,'admin','2024-07-09 08:54:30.329000',1),
(1810478857210576898,'任务流123-1-1',7,1810478857139273730,1,'admin','2024-07-09 08:59:31.257000',0);

/*Table structure for table `t_task_exam_customer_answer` */

DROP TABLE IF EXISTS `t_task_exam_customer_answer`;

CREATE TABLE `t_task_exam_customer_answer` (
  `id` bigint NOT NULL,
  `task_exam_id` bigint DEFAULT NULL,
  `text_content_id` bigint DEFAULT NULL,
  `create_user` bigint DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_task_exam_customer_answer` */

insert  into `t_task_exam_customer_answer`(`id`,`task_exam_id`,`text_content_id`,`create_user`,`create_time`) values 
(1810515755723440130,1810478857210576898,1810515755660525569,1810229667467235328,'2024-07-09 11:26:08.511000');

/*Table structure for table `t_text_content` */

DROP TABLE IF EXISTS `t_text_content`;

CREATE TABLE `t_text_content` (
  `id` bigint NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_text_content` */

insert  into `t_text_content`(`id`,`content`,`create_time`) values 
(1810215047849598977,'{\"titleContent\":\"体术1-1\",\"analyze\":\"an---1111\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"1\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"2\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"3\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"4\",\"score\":null,\"itemUuid\":null}],\"correct\":\"A\"}','2024-07-08 15:31:14.195000'),
(1810215785770319873,'{\"titleContent\":\"体术1-2\",\"analyze\":\"这选1-2\",\"questionItemObjects\":[{\"prefix\":\"A\",\"content\":\"22\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"B\",\"content\":\"33\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"C\",\"content\":\"44\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"D\",\"content\":\"55\",\"score\":null,\"itemUuid\":null}],\"correct\":\"\"}','2024-07-08 15:34:10.142000'),
(1810225045621051392,'[{\"name\":\"体基础\",\"questionItems\":[{\"id\":1810215785824845825,\"itemOrder\":1},{\"id\":1810215047916707842,\"itemOrder\":2}]}]','2024-07-08 16:10:57.877000'),
(1810225789036269568,'[]','2024-07-08 16:13:55.070000'),
(1810225953692061696,'[{\"name\":\"体术低1\",\"questionItems\":[{\"id\":1810215785824845825,\"itemOrder\":1},{\"id\":1810215047916707842,\"itemOrder\":2}]}]','2024-07-08 16:14:34.385000'),
(1810228272307507200,'[{\"name\":\"体术11\",\"questionItems\":[{\"id\":1810215047916707842,\"itemOrder\":1},{\"id\":1810215785824845825,\"itemOrder\":2}]}]','2024-07-08 16:23:47.181000'),
(1810229444659679233,'{\"titleContent\":\"幻1-3\",\"analyze\":\"选是\",\"questionItemObjects\":[{\"prefix\":\"对\",\"content\":\"是\",\"score\":null,\"itemUuid\":null},{\"prefix\":\"错\",\"content\":\"否\",\"score\":null,\"itemUuid\":null}],\"correct\":\"对\"}','2024-07-08 16:28:26.674000'),
(1810236990348726272,'[{\"name\":\"\",\"questionItems\":[{\"id\":1810215047916707842,\"itemOrder\":1},{\"id\":1810215785824845825,\"itemOrder\":2}]}]','2024-07-08 16:58:25.725000'),
(1810466746191335426,'[{\"examPaperId\":\"1810236990478749696\",\"examPaperName\":\"任务试卷1111\",\"itemOrder\":null}]','2024-07-09 08:11:23.752000'),
(1810477505489014784,'[{\"name\":\"\",\"questionItems\":[{\"id\":1810215047916707842,\"itemOrder\":1},{\"id\":1810215785824845825,\"itemOrder\":2}]}]','2024-07-09 08:54:09.005000'),
(1810477594930606081,'[{\"examPaperId\":\"1810477505577095168\",\"examPaperName\":\"任务试卷11111222\",\"itemOrder\":null}]','2024-07-09 08:54:30.329000'),
(1810478773213204480,'[{\"name\":\"\",\"questionItems\":[{\"id\":1810215785824845825,\"itemOrder\":1},{\"id\":1810215047916707842,\"itemOrder\":2}]}]','2024-07-09 08:59:11.244000'),
(1810478857139273730,'[{\"examPaperId\":\"1810478773339033600\",\"examPaperName\":\"初一任务123\",\"itemOrder\":null}]','2024-07-09 08:59:31.257000'),
(1810515755660525569,'[{\"examPaperId\":\"1810478773339033600\",\"examPaperAnswerId\":\"1810515755400478722\",\"status\":2}]','2024-07-09 11:26:08.511000'),
(1810554490546425856,'[{\"name\":\"\",\"questionItems\":[{\"id\":1810229444735176706,\"itemOrder\":1}]}]','2024-07-09 14:00:03.665000'),
(1810554665377599488,'[{\"name\":\"\",\"questionItems\":[{\"id\":1810229444735176706,\"itemOrder\":1}]}]','2024-07-09 14:00:45.360000'),
(1810554828162732032,'[{\"name\":\"123\",\"questionItems\":[{\"id\":1810215785824845825,\"itemOrder\":1}]}]','2024-07-09 14:01:24.172000');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL,
  `user_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `birth_day` timestamp(6) NULL DEFAULT NULL,
  `user_level` int DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` bigint DEFAULT NULL,
  `status` int DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `modify_time` timestamp(6) NULL DEFAULT NULL,
  `last_active_time` timestamp(6) NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`user_uuid`,`user_name`,`password`,`real_name`,`age`,`sex`,`birth_day`,`user_level`,`phone`,`role`,`status`,`image_path`,`create_time`,`modify_time`,`last_active_time`,`deleted`,`wx_open_id`) values 
(1,NULL,'admin','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','卡卡西',41,1,'2005-01-08 08:00:00.000000',NULL,'13333333333',3,0,NULL,'2024-07-08 10:30:51.000000','2024-07-08 10:44:58.662000',NULL,0,NULL),
(1810143182934302721,'5ed37ee5-4cd3-4ca8-8dbf-2caf66092494','admin1','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','伊鲁卡',21,2,'2024-07-29 08:00:00.000000',NULL,'1',3,0,NULL,'2024-07-08 10:45:40.290000',NULL,'2024-07-08 10:45:40.290000',0,NULL),
(1810150836349833216,'ede26410-8110-4a29-a85d-f012bbd52798','admin2','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','红',1,2,'2024-08-01 08:00:00.000000',NULL,'1',3,0,NULL,'2024-07-08 11:16:05.019000','2024-07-08 11:19:10.078000','2024-07-08 11:16:05.019000',0,NULL),
(1810229667467235328,'c4a25b4e-64d3-4a4b-81e4-24ac1892452a','s1','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','小李',13,1,'2024-07-30 08:00:00.000000',7,'11111222333',1,0,NULL,'2024-07-08 16:29:19.822000','2024-07-09 17:21:51.664000','2024-07-08 16:29:19.822000',0,'oWrCC4mxBZKwkUWYEL_b6zDvlMo0'),
(1810574341084155904,'e27ac1ec-8756-4c29-9a04-f2994b24a9f6','s2','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','同学2',14,2,'2024-07-09 00:00:00.000000',7,'1',1,0,NULL,'2024-07-09 15:18:56.417000','2024-07-09 15:19:30.910000','2024-07-09 15:18:56.417000',0,NULL);

/*Table structure for table `t_user_event_log` */

DROP TABLE IF EXISTS `t_user_event_log`;

CREATE TABLE `t_user_event_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1810603789869387779 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_event_log` */

insert  into `t_user_event_log`(`id`,`user_id`,`user_name`,`real_name`,`content`,`create_time`) values 
(1810515755853463553,1810229667467235328,'s1','小李','s1 提交试卷：初一任务123 得分：5 耗时：7 秒','2024-07-09 11:26:08.500000'),
(1810549159844155394,1810229667467235328,'s1','小李','s1 提交试卷：体术地基12 得分：0 耗时：9 秒','2024-07-09 13:38:52.639000'),
(1810549276827488257,1810229667467235328,'s1','小李','s1 提交试卷：体术地基12 得分：0 耗时：3 秒','2024-07-09 13:39:20.576000'),
(1810558332791205889,1810229667467235328,'s1','小李','s1 提交试卷：体术地基12 得分：10 耗时：2 秒','2024-07-09 14:15:19.584000'),
(1810570760455229441,1810229667467235328,'s1','小李','s1 更新了个人资料','2024-07-09 15:04:42.723000'),
(1810574341375856642,1810574341084155904,'s2',NULL,'欢迎 s2 注册来到学之思开源考试系统','2024-07-09 15:18:56.480000'),
(1810574486234533889,1810574341084155904,'s2','同学2','s2 更新了个人资料','2024-07-09 15:19:31.033000'),
(1810603789869387778,1810229667467235328,'s1','小李','s1 提交试卷：体术地基12 得分：0 耗时：4 秒','2024-07-09 17:15:57.462000');

/*Table structure for table `t_user_token` */

DROP TABLE IF EXISTS `t_user_token`;

CREATE TABLE `t_user_token` (
  `id` bigint NOT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `end_time` timestamp(6) NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `t_user_token` */

insert  into `t_user_token`(`id`,`token`,`user_id`,`wx_open_id`,`create_time`,`end_time`,`user_name`) values 
(1810591328634224641,'eec02391-bf0a-432f-91b3-e0e5a9ce0cfa',1810229667467235328,'oWrCC4mxBZKwkUWYEL_b6zDvlMo0','2024-07-09 16:26:26.534000','2024-07-10 04:26:26.534000','s1'),
(1810605275223478274,'abe5e40b-3d0b-401f-96d6-1c5271f4d7f0',1810229667467235328,'oWrCC4mxBZKwkUWYEL_b6zDvlMo0','2024-07-09 17:21:51.680000','2024-07-10 05:21:51.680000','s1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
