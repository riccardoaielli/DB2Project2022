-- MySQL 
--
-- Current Database: `db2Project`
--

-- USE `db2Project`;
-- INSERT INTO `service` VALUES (1,'MOBILE_PHONE',5.0, 5.0, 500, 1000, 0.0, 0);
-- INSERT INTO `service` VALUES (2,'FIXED_PHONE',5.0, 0.0, 2000, 0, 0.0, 0);
-- INSERT INTO `service` VALUES (3,'MOBILE_INTERNET',0.0, 0.0, 0, 0, 20.0, 15);
-- INSERT INTO `service` VALUES (4,'FIXED_INTERNET',0.0, 0.0, 0, 0, 30.0, 25);


DROP SCHEMA `db2Project`;
CREATE DATABASE IF NOT EXISTS `db2Project`;

USE `db2Project`;

-- ok
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(90) NOT NULL,
  `Flag_Ins` binary default 0 NOT NULL,
  PRIMARY KEY (`id`),
  constraint Email
        unique (Email),
    constraint Id
        unique (Id),
    constraint Username
        unique (Username)
);

-- ok
-- Data for table `user`
--

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'user','password','user@prova.com','0');
UNLOCK TABLES;

-- ok
-- Table structure for table `validity_period`
--

DROP TABLE IF EXISTS `validity_period`;

CREATE TABLE `validity_period` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Monthly_fee` int NOT NULL,
  `Months` int NOT NULL,
  PRIMARY KEY (`Id`)
);

-- ok
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;

CREATE TABLE `alert` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Amount` int NOT NULL,
  `Timestamp` datetime NOT NULL,
  `User_alert_id` int NOT NULL,
  PRIMARY KEY (`Id`),                                                                                                                                                                        
  UNIQUE KEY (`User_alert_id`),                                                                                                                                                    
  CONSTRAINT `user_alert` FOREIGN KEY (`User_alert_id`) REFERENCES `user` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT                                                                          
);

-- ok
-- Data for table `alert`
--

-- LOCK TABLES `alert` WRITE;
-- INSERT INTO `alert` VALUES (1,'2','2022-02-24 23:46:55',3);
-- UNLOCK TABLES;

-- ok
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  constraint Username
        unique (Username)
);

-- ok
-- Data for table `employee`
--

LOCK TABLES `employee` WRITE;
INSERT INTO `employee` VALUES (1,'employee','password');
UNLOCK TABLES;

-- ok
-- Table structure for table `employeeServicePack`
--

DROP TABLE IF EXISTS `employeeServicePack`;

CREATE TABLE `employeeServicePack` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY (`Name`)
);

LOCK TABLES `employeeServicePack` WRITE;
INSERT INTO `employeeServicePack` VALUES (1,'Special new');
INSERT INTO `employeeServicePack` VALUES (2,'Young special');
UNLOCK TABLES;

-- ok
-- Table structure for table `optional_product`
--

DROP TABLE IF EXISTS `optional_product`;

CREATE TABLE `optional_product` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Fee` float NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY (`Name`)
);

-- ok
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  `Min_fee` float,
  `Sms_fee` float,
  `Min` int,
  `Sms` int,
  `Gb_fee` float,
  `Gb` int,
  PRIMARY KEY (`Id`)
);

-- USE `db2Project`;
-- INSERT INTO `service` VALUES (1,'MOBILE_PHONE',5.0, 5.0, 500, 1000, 0.0, 0);
-- INSERT INTO `service` VALUES (2,'FIXED_PHONE',5.0, 0.0, 2000, 0, 0.0, 0);
-- INSERT INTO `service` VALUES (3,'MOBILE_INTERNET',0.0, 0.0, 0, 0, 20.0, 15);
-- INSERT INTO `service` VALUES (4,'FIXED_INTERNET',0.0, 0.0, 0, 0, 30.0, 25);

-- ok
-- Table structure for table `service_pack`
--

DROP TABLE IF EXISTS `service_pack`;

CREATE TABLE `service_pack` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Validity_period_id` int NOT NULL,
  `Start_date` date NOT NULL,
  `Deactivation_date` date NOT NULL,
  `Costpackage` float NOT NULL, -- Calcolato tramite il validity_period scelto
  `Totalcostoptionalproducts` float DEFAULT 0 NOT NULL, -- Somma del costo degli optional_product scelti
  `Service_pack_employee_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY (`Validity_period_id`),
  CONSTRAINT `validity_period_fk` FOREIGN KEY (`Validity_period_id`) REFERENCES `validity_period` (`Id`),
  CONSTRAINT `service_pack_employee_fk` FOREIGN KEY (`Service_pack_employee_id`) REFERENCES `employeeServicePack` (`Id`)
);

-- ok
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Totalcost` float NOT NULL,
  `Isvalid` binary NOT NULL,
  `Timestamp` datetime NOT NULL,
  `User_id` int NOT NULL,
  `Service_pack_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  CONSTRAINT `service_pack_id` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`),
  CONSTRAINT `user_id` FOREIGN KEY (`User_id`) REFERENCES `user` (`Id`)
);

-- ok
-- Table structure for table `has`
--

DROP TABLE IF EXISTS `has`;

CREATE TABLE `has` (
  `Service_pack_id` int NOT NULL,
  `Optional_product_id` int NOT NULL,
  PRIMARY KEY (`Service_pack_id`,`Optional_product_id`),
  CONSTRAINT `has_ibfk_1` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `has_ibfk_2` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- ok
-- Table structure for table `comprises`
--

DROP TABLE IF EXISTS `comprises`;

CREATE TABLE `comprises` (
  `EmployeeServicePack_id` int NOT NULL,
  `Service_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Service_id`),
  CONSTRAINT `comprises_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comprises_ibfk_2` FOREIGN KEY (`Service_id`) REFERENCES `service` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- ok
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;

CREATE TABLE `offers` (
  `EmployeeServicePack_id` int NOT NULL,
  `Validity_period_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Validity_period_id`),
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`Validity_period_id`) REFERENCES `validity_period` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);
