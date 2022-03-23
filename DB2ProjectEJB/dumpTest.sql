CREATE DATABASE  IF NOT EXISTS `db2Project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db2Project`;
-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: localhost    Database: db2Project
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Amount` float NOT NULL,
  `Timestamp` datetime NOT NULL,
  `User_alert_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `User_alert_id` (`User_alert_id`),
  CONSTRAINT `user_alert` FOREIGN KEY (`User_alert_id`) REFERENCES `user` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alerts`
--

DROP TABLE IF EXISTS `alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alerts` (
  `Alert_id` int NOT NULL,
  PRIMARY KEY (`Alert_id`),
  CONSTRAINT `Alerts_fk0` FOREIGN KEY (`Alert_id`) REFERENCES `alert` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerts`
--

LOCK TABLES `alerts` WRITE;
/*!40000 ALTER TABLE `alerts` DISABLE KEYS */;
/*!40000 ALTER TABLE `alerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `averageOPwithESP`
--

DROP TABLE IF EXISTS `averageOPwithESP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `averageOPwithESP` (
  `EmployeeServicePack_id` int NOT NULL,
  `averageOPs` float NOT NULL DEFAULT '0',
  `totalOPsPerESP` int NOT NULL DEFAULT '0',
  `totalOrdersPerESP` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`EmployeeServicePack_id`),
  CONSTRAINT `averageOPs_fk0` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `averageOPwithESP`
--

LOCK TABLES `averageOPwithESP` WRITE;
/*!40000 ALTER TABLE `averageOPwithESP` DISABLE KEYS */;
INSERT INTO `averageOPwithESP` VALUES (1,0,0,0),(2,0,0,0),(3,0,0,0),(4,0,0,0);
/*!40000 ALTER TABLE `averageOPwithESP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `best_seller_OP`
--

DROP TABLE IF EXISTS `best_seller_OP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `best_seller_OP` (
  `Optional_product_id` int NOT NULL,
  `totalSales` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`Optional_product_id`),
  CONSTRAINT `bs_fk0` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `best_seller_OP`
--

LOCK TABLES `best_seller_OP` WRITE;
/*!40000 ALTER TABLE `best_seller_OP` DISABLE KEYS */;
/*!40000 ALTER TABLE `best_seller_OP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comprises`
--

DROP TABLE IF EXISTS `comprises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comprises` (
  `EmployeeServicePack_id` int NOT NULL,
  `Service_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Service_id`),
  KEY `comprises_ibfk_2` (`Service_id`),
  CONSTRAINT `comprises_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comprises_ibfk_2` FOREIGN KEY (`Service_id`) REFERENCES `service` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comprises`
--

LOCK TABLES `comprises` WRITE;
/*!40000 ALTER TABLE `comprises` DISABLE KEYS */;
INSERT INTO `comprises` VALUES (1,1),(2,1),(3,1),(4,1),(1,2),(2,2),(3,2),(4,2);
/*!40000 ALTER TABLE `comprises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'employee','password');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employeeServicePack`
--

DROP TABLE IF EXISTS `employeeServicePack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employeeServicePack` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employeeServicePack`
--

LOCK TABLES `employeeServicePack` WRITE;
/*!40000 ALTER TABLE `employeeServicePack` DISABLE KEYS */;
INSERT INTO `employeeServicePack` VALUES (2,'ESP2'),(3,'ESP3'),(4,'ESP4'),(1,'Provar');
/*!40000 ALTER TABLE `employeeServicePack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `has`
--

DROP TABLE IF EXISTS `has`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `has` (
  `Service_pack_id` int NOT NULL,
  `Optional_product_id` int NOT NULL,
  PRIMARY KEY (`Service_pack_id`,`Optional_product_id`),
  KEY `has_ibfk_2` (`Optional_product_id`),
  CONSTRAINT `has_ibfk_1` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `has_ibfk_2` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has`
--

LOCK TABLES `has` WRITE;
/*!40000 ALTER TABLE `has` DISABLE KEYS */;
/*!40000 ALTER TABLE `has` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insolvent`
--

DROP TABLE IF EXISTS `insolvent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insolvent` (
  `User_id` int NOT NULL,
  PRIMARY KEY (`User_id`),
  CONSTRAINT `insolvent_fk0` FOREIGN KEY (`User_id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insolvent`
--

LOCK TABLES `insolvent` WRITE;
/*!40000 ALTER TABLE `insolvent` DISABLE KEYS */;
/*!40000 ALTER TABLE `insolvent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `numberTotalPurchasesPerESP`
--

DROP TABLE IF EXISTS `numberTotalPurchasesPerESP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `numberTotalPurchasesPerESP` (
  `EmployeeServicePack_id` int NOT NULL,
  `Numbertotalpurchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`EmployeeServicePack_id`),
  CONSTRAINT `numberOfTotalPurchasesPerPackage` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `numberTotalPurchasesPerESP`
--

LOCK TABLES `numberTotalPurchasesPerESP` WRITE;
/*!40000 ALTER TABLE `numberTotalPurchasesPerESP` DISABLE KEYS */;
INSERT INTO `numberTotalPurchasesPerESP` VALUES (1,0),(2,0),(3,0),(4,0);
/*!40000 ALTER TABLE `numberTotalPurchasesPerESP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `numberTotalPurchasesPerESPAndValidityPeriod`
--

DROP TABLE IF EXISTS `numberTotalPurchasesPerESPAndValidityPeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `numberTotalPurchasesPerESPAndValidityPeriod` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `EmployeeServicePack_id` int NOT NULL,
  `Validity_period_id` int NOT NULL,
  `TotalPurchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `numberTotalPurchasesPerESPAndValidityPeriod_fk0` (`EmployeeServicePack_id`),
  KEY `numberTotalPurchasesPerESPAndValidityPeriod_fk1` (`Validity_period_id`),
  CONSTRAINT `numberTotalPurchasesPerESPAndValidityPeriod_fk0` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`),
  CONSTRAINT `numberTotalPurchasesPerESPAndValidityPeriod_fk1` FOREIGN KEY (`Validity_period_id`) REFERENCES `validity_period` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `numberTotalPurchasesPerESPAndValidityPeriod`
--

LOCK TABLES `numberTotalPurchasesPerESPAndValidityPeriod` WRITE;
/*!40000 ALTER TABLE `numberTotalPurchasesPerESPAndValidityPeriod` DISABLE KEYS */;
INSERT INTO `numberTotalPurchasesPerESPAndValidityPeriod` VALUES (1,1,1,0),(2,1,2,0),(3,2,2,0),(4,2,3,0),(5,2,4,0),(6,3,1,0),(7,3,2,0),(8,4,1,0),(9,4,2,0),(10,4,3,0);
/*!40000 ALTER TABLE `numberTotalPurchasesPerESPAndValidityPeriod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offers` (
  `EmployeeServicePack_id` int NOT NULL,
  `Validity_period_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Validity_period_id`),
  KEY `offers_ibfk_2` (`Validity_period_id`),
  CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `offers_ibfk_2` FOREIGN KEY (`Validity_period_id`) REFERENCES `validity_period` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (1,1),(3,1),(4,1),(1,2),(2,2),(3,2),(4,2),(2,3),(4,3),(2,4);
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optional_product`
--

DROP TABLE IF EXISTS `optional_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optional_product` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Fee` float NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optional_product`
--

LOCK TABLES `optional_product` WRITE;
/*!40000 ALTER TABLE `optional_product` DISABLE KEYS */;
INSERT INTO `optional_product` VALUES (1,'OPT1',10),(2,'OPT2',20),(3,'OPT3',30),(4,'OPT4',40);
/*!40000 ALTER TABLE `optional_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Totalcost` float NOT NULL,
  `Isvalid` tinyint NOT NULL,
  `Timestamp` datetime NOT NULL,
  `User_id` int NOT NULL,
  `Service_pack_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Service_pack_id` (`Service_pack_id`),
  KEY `user_id` (`User_id`),
  CONSTRAINT `service_pack_id` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`),
  CONSTRAINT `user_id` FOREIGN KEY (`User_id`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propose`
--

DROP TABLE IF EXISTS `propose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propose` (
  `EmployeeServicePack_id` int NOT NULL,
  `Optional_product_id` int NOT NULL,
  PRIMARY KEY (`EmployeeServicePack_id`,`Optional_product_id`),
  KEY `propose_ibfk_2` (`Optional_product_id`),
  CONSTRAINT `propose_ibfk_1` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `propose_ibfk_2` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propose`
--

LOCK TABLES `propose` WRITE;
/*!40000 ALTER TABLE `propose` DISABLE KEYS */;
INSERT INTO `propose` VALUES (1,1),(3,1),(4,1),(3,2),(4,2),(2,3),(3,3),(2,4),(4,4);
/*!40000 ALTER TABLE `propose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rejectedOrders`
--

DROP TABLE IF EXISTS `rejectedOrders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rejectedOrders` (
  `Order_id` int NOT NULL,
  PRIMARY KEY (`Order_id`),
  CONSTRAINT `rejectedOrders_fk0` FOREIGN KEY (`Order_id`) REFERENCES `order` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rejectedOrders`
--

LOCK TABLES `rejectedOrders` WRITE;
/*!40000 ALTER TABLE `rejectedOrders` DISABLE KEYS */;
/*!40000 ALTER TABLE `rejectedOrders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesPerPackage`
--

DROP TABLE IF EXISTS `salesPerPackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salesPerPackage` (
  `EmployeeServicePack_id` int NOT NULL,
  `totalSalesWithOptionalProduct` int NOT NULL DEFAULT '0',
  `totalSalesWithoutOptionalProduct` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`EmployeeServicePack_id`),
  CONSTRAINT `salesPerPackage_fk0` FOREIGN KEY (`EmployeeServicePack_id`) REFERENCES `employeeServicePack` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesPerPackage`
--

LOCK TABLES `salesPerPackage` WRITE;
/*!40000 ALTER TABLE `salesPerPackage` DISABLE KEYS */;
INSERT INTO `salesPerPackage` VALUES (1,0,0),(2,0,0),(3,0,0),(4,0,0);
/*!40000 ALTER TABLE `salesPerPackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  `Min_fee` float DEFAULT NULL,
  `Sms_fee` float DEFAULT NULL,
  `Min` int DEFAULT NULL,
  `Sms` int DEFAULT NULL,
  `Gb_fee` float DEFAULT NULL,
  `Gb` int DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'MOBILE_PHONE',5,5,500,1000,NULL,NULL),(2,'FIXED_PHONE',5,NULL,2000,NULL,NULL,NULL),(3,'MOBILE_INTERNET',NULL,NULL,NULL,NULL,20,15),(4,'FIXED_INTERNET',NULL,NULL,NULL,NULL,30,25),(5,'FIXED_INTERNET',NULL,NULL,NULL,NULL,45,30);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_pack`
--

DROP TABLE IF EXISTS `service_pack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_pack` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Validity_period_id` int NOT NULL,
  `Start_date` date NOT NULL,
  `Deactivation_date` date NOT NULL,
  `Costpackage` float NOT NULL,
  `Totalcostoptionalproducts` float NOT NULL DEFAULT '0',
  `Service_pack_employee_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `validity_period_fk` (`Validity_period_id`),
  KEY `service_pack_employee_fk` (`Service_pack_employee_id`),
  CONSTRAINT `service_pack_employee_fk` FOREIGN KEY (`Service_pack_employee_id`) REFERENCES `employeeServicePack` (`Id`),
  CONSTRAINT `validity_period_fk` FOREIGN KEY (`Validity_period_id`) REFERENCES `validity_period` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_pack`
--

LOCK TABLES `service_pack` WRITE;
/*!40000 ALTER TABLE `service_pack` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_pack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `totalSalesPerOP`
--

DROP TABLE IF EXISTS `totalSalesPerOP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `totalSalesPerOP` (
  `Optional_product_id` int NOT NULL,
  `totalSales` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`Optional_product_id`),
  CONSTRAINT `totalSalesPerOP_fk1` FOREIGN KEY (`Optional_product_id`) REFERENCES `optional_product` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `totalSalesPerOP`
--

LOCK TABLES `totalSalesPerOP` WRITE;
/*!40000 ALTER TABLE `totalSalesPerOP` DISABLE KEYS */;
INSERT INTO `totalSalesPerOP` VALUES (1,0),(2,0),(3,0),(4,0);
/*!40000 ALTER TABLE `totalSalesPerOP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(90) NOT NULL,
  `Flag_Ins` tinyint(1) NOT NULL DEFAULT '0',
  `NumberOfFailedPayments` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `Id` (`Id`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user','password','user@prova.com',0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validity_period`
--

DROP TABLE IF EXISTS `validity_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validity_period` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Monthly_fee` int NOT NULL,
  `Months` int NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validity_period`
--

LOCK TABLES `validity_period` WRITE;
/*!40000 ALTER TABLE `validity_period` DISABLE KEYS */;
INSERT INTO `validity_period` VALUES (1,20,6),(2,15,12),(3,10,24),(4,8,30);
/*!40000 ALTER TABLE `validity_period` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-23 12:16:14
