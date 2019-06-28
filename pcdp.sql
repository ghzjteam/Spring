CREATE DATABASE  IF NOT EXISTS `pcdp` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pcdp`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: pcdp
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `code` (
  `code_id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `code_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `file` (
  `file_id` int(10) NOT NULL AUTO_INCREMENT,
  `project_id` int(10) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filesystem`
--

DROP TABLE IF EXISTS `filesystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `filesystem` (
  `file_id` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `parent_id` char(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `filesystem_ibfk_1` (`parent_id`),
  CONSTRAINT `filesystem_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `filesystem` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filesystem`
--

LOCK TABLES `filesystem` WRITE;
/*!40000 ALTER TABLE `filesystem` DISABLE KEYS */;
INSERT INTO `filesystem` VALUES ('001','root','folder',NULL,NULL),('1','root','folder',NULL,NULL),('190618165856F92','会议视频1','folder','001',''),('1906181703264B6','会议视频2','folder','001','这是下午的会议'),('190618180529E15','成本控制','folder','1906181703264B6','1人缺到'),('190618180851B45','readme.md','file','001','Nanjing'),('1906181855423FD','成本','folder','190618180529E15','北广场成本'),('190618193602A42','成员分工','folder','001','次小组'),('2','root','folder',NULL,NULL),('3','root','folder',NULL,NULL),('4','root','folder',NULL,NULL),('5','root','folder',NULL,NULL),('6','root','folder',NULL,NULL),('7','root','folder',NULL,NULL);
/*!40000 ALTER TABLE `filesystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `meeting` (
  `meeting_id` int(10) NOT NULL AUTO_INCREMENT,
  `team_id` int(10) DEFAULT NULL,
  `meeting_name` varchar(20) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`meeting_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `meeting_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
INSERT INTO `meeting` VALUES (1,1,'启动会议','创建团队','临时会议','Meeting9.docx','2019-06-10 12:30:00','D308'),(2,1,'立项会议','创建项目','临时会议','会议1.docx','2019-06-10 12:30:00','D301'),(3,2,'审核会议','创建任务','定期会议','会议1.docx','2019-06-10 12:30:00','D202'),(4,2,'检查会议','创建工程','临时会议','会议1.docx','2019-05-30 15:30:00','D202'),(5,2,'结束会议','工程审核','定期会议','会议1.docx','2019-05-31 21:00:00','D333'),(6,1,'终止会议','创建团队','临时会议','会议1.docx','2020-06-07 23:00:00','D308'),(7,3,'决策会议','标准评定','定期会议','会议1.docx','2019-07-03 16:30:00','D333'),(8,3,'行政会议','项目确定','临时会议','会议1.docx','2019-06-20 19:30:00','D302'),(22,1,'姜新炜','5','定期会议',NULL,'2019-06-03 23:59:00','分');
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project` (
  `project_id` int(10) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(20) DEFAULT NULL,
  `team_id` int(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `team_id` (`team_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'团队管理模块',1,'2019-05-15 21:19:14','团队信息123'),(3,'选课功能',1,'2019-05-01 21:26:03','学生选课'),(4,'ATM机',2,'2019-05-07 21:26:53','ATM操作'),(5,'图书管理',2,'2019-06-12 19:34:33','管理图书'),(6,'预定图书',3,'2019-06-28 19:30:00','图书预定'),(8,'个人管理模块',1,NULL,'asd啊'),(9,'团队管理',1,NULL,'备注');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `team` (
  `team_id` int(10) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(20) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'6','6','2019-05-25 22:23:01'),(2,'TeamSky','sky','2019-05-09 22:23:58'),(3,'TeamGround','ground','2019-05-28 21:27:31'),(4,'程序员2','2','2019-06-16 14:31:23');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `photo` varchar(255) DEFAULT 'head.png',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Stove1','123456','1529955471@qq.com','head.png','1201191100','男','2019-05-08 22:21:20'),(2,'Jude','123','514029728@qq.com','head.png','273738282','女','2019-05-07 22:21:32'),(3,'Tory','123','1529955471@qq.com','head.png','120119110','男','2019-05-01 20:59:19'),(4,'Jusus','123','1529955471@qq.com','head.png','227373722','女','2019-05-10 20:59:59'),(5,'姜新炜','123','jiangxinwei1997@gmail.com','head.png','18254635250','男','2019-06-19 23:16:33'),(6,'新炜 Ray','123','jian11@gmail.com','head.png','18254635250','女','2019-06-19 23:17:11'),(7,'1','123','613373@qq.com','head.png','18254635250','女','2019-06-20 08:09:49');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_team`
--

DROP TABLE IF EXISTS `user_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_team` (
  `user_id` int(10) NOT NULL,
  `team_id` int(10) NOT NULL,
  `position` int(2) DEFAULT '0',
  PRIMARY KEY (`user_id`,`team_id`),
  KEY `team_id` (`team_id`),
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `user_team2_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_team2_ibfk_2` FOREIGN KEY (`team_id`) REFERENCES `team` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_team`
--

LOCK TABLES `user_team` WRITE;
/*!40000 ALTER TABLE `user_team` DISABLE KEYS */;
INSERT INTO `user_team` VALUES (1,1,0),(1,2,1),(1,3,2),(1,4,0),(2,1,1),(2,2,0),(2,3,1),(2,4,1),(3,1,2),(3,2,2),(3,3,0),(3,4,2),(4,1,2),(4,2,2),(4,3,2),(5,1,2),(6,2,1);
/*!40000 ALTER TABLE `user_team` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-25 17:45:25
