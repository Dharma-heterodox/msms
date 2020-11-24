/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 5.7.28-0ubuntu0.18.04.4 : Database - school
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`school` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `school`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `address` */

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `hibernate_sequence` */

/*Table structure for table `organization` */

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `org_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` varchar(255) DEFAULT NULL,
  `address` tinyblob,
  `license_no` varchar(255) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `organization_type` varchar(255) DEFAULT NULL,
  `registration_no` varchar(255) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `alternate_phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `organization_sub_type` varchar(255) DEFAULT NULL,
  `parent_org_id` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`org_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `organization` */

insert  into `organization`(`org_id`,`active`,`address`,`license_no`,`organization_name`,`organization_type`,`registration_no`,`tenant_id`,`parent_id`,`alternate_phone`,`email`,`organization_sub_type`,`parent_org_id`,`phone`,`website`) values 
(1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(2,'yes',NULL,NULL,'Test School','school','123',NULL,NULL,NULL,NULL,'cbse','',NULL,NULL),
(3,'yes',NULL,NULL,'Test School','school','123',NULL,NULL,NULL,NULL,'cbse','',NULL,NULL),
(4,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(5,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(6,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(7,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(8,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(9,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,NULL,'cbse','',NULL,NULL),
(10,'yes',NULL,NULL,'Test School','school','123',1,NULL,NULL,'test@test.com','cbse','',NULL,NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role`,`tenant_id`) values 
(1,'ADMIN',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(250) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `first_name` varchar(250) DEFAULT NULL,
  `last_name` varchar(250) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  `contact_no` varchar(255) DEFAULT NULL,
  `emergency_contact_no` varchar(255) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`email`,`password`,`first_name`,`last_name`,`active`,`mobile`,`name`,`tenant_id`,`contact_no`,`emergency_contact_no`,`mobile_no`,`user_type`) values 
(1,'admin','admin@mail.com','$2a$10$oyKb94914t9IwENg3nd4qe2qJRn1pOz/YrXRsQyPQI.CjeQJfuw9G','admin','admin',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(2,'mathi','mathi@mail.com','$2a$10$xRNAs/RA9bnBgf1Ab2HR8uflfLAX7tCowIVkA7pg73NmxROUkdziK','mathi','mathi',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(3,'test@test.com','test@test.com','$2a$10$85g76MjSBTHV97mpuIs7aOp2WftOdj74wIyMue7pjfTUH85Pk5h.2',NULL,NULL,1,NULL,NULL,1,NULL,NULL,NULL,NULL);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values 
(1,1),
(2,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
