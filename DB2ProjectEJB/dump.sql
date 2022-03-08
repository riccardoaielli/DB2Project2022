CREATE DATABASE  IF NOT EXISTS `db2Project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db2Project`;
-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: db2Project
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.21.04.1

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
  `Amount` varchar(45) NOT NULL,
  `Timestamp` varchar(45) NOT NULL,
  `User_alert` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `user_alert_UNIQUE` (`User_alert`),
  KEY `fk_alert_1_idx` (`Id`),
  CONSTRAINT `user_alert` FOREIGN KEY (`User_alert`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comprises`
--

DROP TABLE IF EXISTS `comprises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comprises` (
  `Service_pack_id` int NOT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`Service_pack_id`,`service_id`),
  KEY `service_id` (`service_id`),
  CONSTRAINT `comprises_ibfk_1` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comprises_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `Email` varchar(90) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `optional_product`
--

DROP TABLE IF EXISTS `optional_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optional_product` (
  `Id` int NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Fee` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `name_UNIQUE` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Cost` varchar(45) NOT NULL,
  `Start_date` varchar(45) NOT NULL,
  `Status` varchar(45) NOT NULL,
  `Timestamp` varchar(45) NOT NULL,
  `User_id` int NOT NULL,
  `Service_pack_id` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `user_id_idx` (`User_id`),
  KEY `service_pack_id_idx` (`Service_pack_id`),
  CONSTRAINT `service_pack_id` FOREIGN KEY (`Service_pack_id`) REFERENCES `service_pack` (`Id`),
  CONSTRAINT `user_id` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `Id` int NOT NULL,
  `Type` varchar(45) NOT NULL,
  `Min_fee` int,
  `Sms_fee` int,
  `Min` int,
  `Sms` int,
  `Gb_fee` int,
  `Gb` int,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_pack`
--

DROP TABLE IF EXISTS `service_pack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_pack` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Validity_period` int NOT NULL,
  `Optional_products` int NOT NULL,
  `Services` int NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `name_UNIQUE` (`Name`),
  UNIQUE KEY `validity_period_UNIQUE` (`Validity_period`),
  KEY `service_pack_idx` (`Optional_products`),
  KEY `service_fk_idx` (`Services`),
  CONSTRAINT `optional_product_fk` FOREIGN KEY (`Optional_products`) REFERENCES `optional_product` (`Id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `service_fk` FOREIGN KEY (`Services`) REFERENCES `service` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(90) NOT NULL,
  `Flag_Ins` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `validity_period`
--

DROP TABLE IF EXISTS `validity_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `validity_period` (
  `Id` int NOT NULL,
  `Monthly_fee` int NOT NULL,
  `Months` int NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'db2Project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-08 18:47:57
