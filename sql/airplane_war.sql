# Host: localhost  (Version 8.0.11)
# Date: 2020-05-25 18:25:26
# Generator: MySQL-Front 5.4  (Build 4.153) - http://www.mysqlfront.de/

/*!40101 SET NAMES utf8 */;

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin` int(255) DEFAULT NULL,
  `user_plane_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_bullet_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('11111','11111',0,NULL,NULL),('11114444','11114444',0,NULL,NULL),('111222333','111222333',0,NULL,NULL),('111555','111555',0,NULL,NULL),('123','123',169,'static/image/plane/user_plane_1.png','static/image/bullet/user_bullet_0.png'),('1234','1234',0,NULL,NULL),('159','159',0,NULL,NULL),('1808190126','1808190126',0,NULL,NULL),('1808190127','1808190127',0,NULL,NULL),('1808190148','1808190148',0,NULL,NULL),('1808190149','1808190149',0,NULL,NULL),('1808190159','1808190159',0,'static/image/plane/user_plane_1.png','static/image/bullet/user_bullet_0.png'),('5555','55555',2,'static/image/plane/user_plane_1.png','static/image/bullet/user_bullet_0.png'),('56789','56789',0,NULL,NULL),('admin','admin',985002,'static/image/plane/user_plane_2.png','static/image/bullet/user_bullet_6.png');
