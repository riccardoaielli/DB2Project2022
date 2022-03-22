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
-- Data for table `user`
--

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'user','password','user@prova.com','0','0');
UNLOCK TABLES;

-- ok
-- Data for table `alert`
--

-- LOCK TABLES `alert` WRITE;
-- INSERT INTO `alert` VALUES (1,'2','2022-02-24 23:46:55',3);
-- UNLOCK TABLES;


-- ok
-- Data for table `employee`
--

LOCK TABLES `employee` WRITE;
INSERT INTO `employee` VALUES (1,'employee','password');
UNLOCK TABLES;


LOCK TABLES `employeeServicePack` WRITE;
INSERT INTO `employeeServicePack` VALUES (1,'Special new');
INSERT INTO `employeeServicePack` VALUES (2,'Young special');
INSERT INTO `employeeServicePack` VALUES (3,'Senior special');
UNLOCK TABLES;


LOCK TABLES `optional_product` WRITE;
INSERT INTO `optional_product` VALUES (1,'OPT1', 10);
INSERT INTO `optional_product` VALUES (2,'OPT2', 20);
UNLOCK TABLES;


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


LOCK TABLES `comprises` WRITE;
INSERT INTO `comprises` VALUES (1,1);
INSERT INTO `comprises` VALUES (1,2);
INSERT INTO `comprises` VALUES (2,3);
UNLOCK TABLES;


LOCK TABLES `offers` WRITE;
INSERT INTO `offers` VALUES (1,1);
INSERT INTO `offers` VALUES (1,2);
INSERT INTO `offers` VALUES (1,3);
INSERT INTO `offers` VALUES (2,3);
UNLOCK TABLES;


LOCK TABLES `propose` WRITE;
INSERT INTO `propose` VALUES (1,1);
INSERT INTO `propose` VALUES (1,2);
INSERT INTO `propose` VALUES (2,1);
UNLOCK TABLES;