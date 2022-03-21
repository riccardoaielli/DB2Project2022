-- MySQL 
--
-- Current Database: `db2Project`
--

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
  `Flag_Ins` boolean default 0 NOT NULL,
  `NumberOfFailedPayments` int default 0 NOT NULL,
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
INSERT INTO `user` VALUES (1,'user','password','user@prova.com','0','0');
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
-- Data for table `validity_period`
--

LOCK TABLES `validity_period` WRITE;
INSERT INTO `validity_period` VALUES (1,20,6);
INSERT INTO `validity_period` VALUES (2,15,12);
INSERT INTO `validity_period` VALUES (3,10,24);
INSERT INTO `validity_period` VALUES (4,8,30);
UNLOCK TABLES;

-- ok
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;

CREATE TABLE `alert` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
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
INSERT INTO `employeeServicePack` VALUES (3,'Senior special');
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

LOCK TABLES `optional_product` WRITE;
INSERT INTO `optional_product` VALUES (1,'OPT1', 10);
INSERT INTO `optional_product` VALUES (2,'OPT2', 20);
UNLOCK TABLES;

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

-- ok
-- Data for table `service`
--

LOCK TABLES `service` WRITE;
INSERT INTO `service` (`Id`,`Type`,`Min_fee`,`Sms_fee`,`Min`,`Sms`) VALUES (1,'MOBILE_PHONE',5.0, 5.0, 500, 1000);
INSERT INTO `service` (`Id`,`Type`,`Min_fee`,`Min`) VALUES (2,'FIXED_PHONE',5.0, 2000);
INSERT INTO `service` (`Id`,`Type`,`Gb_fee`,`Gb`) VALUES (3,'MOBILE_INTERNET', 20.0, 15);
INSERT INTO `service` (`Id`,`Type`,`Gb_fee`,`Gb`) VALUES (4,'FIXED_INTERNET', 30.0, 25);
INSERT INTO `service` (`Id`,`Type`,`Gb_fee`,`Gb`) VALUES (5,'FIXED_INTERNET', 45.0, 30);
UNLOCK TABLES;


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
  `Isvalid` tinyint NOT NULL,
  `Timestamp` datetime NOT NULL,
  `User_id` int NOT NULL,
  `Service_pack_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY (`Service_pack_id`),
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

LOCK TABLES `comprises` WRITE;
INSERT INTO `comprises` VALUES (1,1);
INSERT INTO `comprises` VALUES (1,2);
INSERT INTO `comprises` VALUES (2,3);
UNLOCK TABLES;

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

LOCK TABLES `offers` WRITE;
INSERT INTO `offers` VALUES (1,1);
INSERT INTO `offers` VALUES (1,2);
INSERT INTO `offers` VALUES (1,3);
INSERT INTO `offers` VALUES (2,3);
UNLOCK TABLES;

-- ok
-- Table structure for table `propose`
--

DROP TABLE IF EXISTS `propose`;

CREATE TABLE `propose` (
  `EmployeeServicePack_id` int NOT NULL,
  `Optional_product_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Optional_product_id`),
  CONSTRAINT `propose_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `propose_ibfk_2` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

LOCK TABLES `propose` WRITE;
INSERT INTO `propose` VALUES (1,1);
INSERT INTO `propose` VALUES (1,2);
INSERT INTO `propose` VALUES (2,1);
UNLOCK TABLES;
