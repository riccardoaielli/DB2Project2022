
USE db2Project;
-- Number of total purchases per package
DROP TABLE IF EXISTS numberTotalPurchasesPerESP;

create table numberTotalPurchasesPerESP
(
    EmployeeServicePack_id int not null primary key,
    Numbertotalpurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage foreign key (EmployeeServicePack_id) references employeeServicePack (Id)
);


DROP TRIGGER IF EXISTS purchaseToNumberTotalPurchasesPerESP_add;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER purchaseToNumberTotalPurchasesPerESP_add
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    IF NEW.Isvalid = 1 THEN
UPDATE numberTotalPurchasesPerESP SET Numbertotalpurchases = Numbertotalpurchases + 1
WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                     FROM service_pack s
                     WHERE s.Id = NEW.Service_pack_id);
END IF;
end //
delimiter ;

DROP TRIGGER IF EXISTS numberTotalPurchasesPerESP_create;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER numberTotalPurchasesPerESP_create
    AFTER INSERT ON employeeServicePack FOR EACH ROW
BEGIN
INSERT INTO db2Project.numberTotalPurchasesPerESP(EmployeeServicePack_id)
VALUES(NEW.Id);
end //
delimiter ;

--  TO BE TESTED *********************************
DROP TRIGGER IF EXISTS purchaseToNumberTotalPurchasesPerESP_update;
delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER purchaseToNumberTotalPurchasesPerESP_update
    AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
    IF NEW.Isvalid = 1 THEN
UPDATE numberTotalPurchasesPerESP SET Numbertotalpurchases = Numbertotalpurchases + 1
WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                     FROM service_pack s
                     WHERE s.Id = NEW.Service_pack_id);
END IF;
end //
delimiter ;

-- *********************************

-- Number of total purchases per package and validity period

DROP TABLE IF EXISTS numberTotalPurchasesPerESPAndValidityPeriod;

create table numberTotalPurchasesPerESPAndValidityPeriod
(	
	Id int not null AUTO_INCREMENT primary key, 
    EmployeeServicePack_id     int not null,
    Validity_period_id   int not null,
    TotalPurchases int not null DEFAULT 0,
    constraint numberTotalPurchasesPerESPAndValidityPeriod_fk0
        foreign key (EmployeeServicePack_id) references employeeServicePack (Id),
    constraint numberTotalPurchasesPerESPAndValidityPeriod_fk1
        foreign key (Validity_period_id) references validity_period (Id)
);


DROP TRIGGER IF EXISTS numberTotalPurchasesPerESPAndVP_create;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER numberTotalPurchasesPerESPAndVP_create
    AFTER INSERT ON offers FOR EACH ROW BEGIN
    INSERT INTO numberTotalPurchasesPerESPAndValidityPeriod(EmployeeServicePack_id, Validity_period_id)
    VALUES(NEW.EmployeeServicePack_id, NEW.Validity_period_id);

end //
delimiter ;
    
    

DROP TRIGGER IF EXISTS purchaseToNumberTotalPurchasesPerESPAndVP_new;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER purchaseToNumberTotalPurchasesPerESPAndVP_new
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.Isvalid = 1 THEN
UPDATE numberTotalPurchasesPerESPAndValidityPeriod 
SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);
                                     
END IF;
end //
delimiter ;


--  TO BE TESTED *********************************
DROP TRIGGER IF EXISTS purchaseToNumberTotalPurchasesPerESPAndVP_update;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER purchaseToNumberTotalPurchasesPerESPAndVP_update
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
                                   IF NEW.Isvalid = 1 THEN
UPDATE db2Project.numberTotalPurchasesPerESPAndValidityPeriod SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);

END IF;
end //
delimiter ;

-- *********************************



-- Total value of sales per package with and without the optional products

DROP TABLE IF EXISTS salesPerPackage;

create table salesPerPackage
(
    EmployeeServicePack_id int not null
        primary key,
    totalSalesWithOptionalProduct int not null DEFAULT 0,
    totalSalesWithoutOptionalProduct int not null DEFAULT 0,
    constraint salesPerPackage_fk0
        foreign key (EmployeeServicePack_id) references employeeServicePack (Id)
);


DROP TRIGGER IF EXISTS ESPAddEntryInSalesPerPackage_create;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER ESPAddEntryInSalesPerPackage_create
    AFTER INSERT ON employeeServicePack FOR EACH ROW BEGIN

    INSERT INTO salesPerPackage(EmployeeServicePack_id)
    VALUES(NEW.Id);

end //
delimiter ;



DROP TRIGGER IF EXISTS salesPerPackage_add;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER salesPerPackage_add
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    DECLARE cp,tcop float;
    IF NEW.Isvalid = 1 THEN --     --------------- perchè hai cambiato?
SET cp := 	(SELECT sp.Costpackage
			FROM service_pack sp
			WHERE sp.Id = NEW.Service_pack_id);
			
SET tcop := 	(SELECT sp.Totalcostoptionalproducts
				FROM service_pack sp
				WHERE sp.Id = NEW.Service_pack_id);


UPDATE salesPerPackage s -- ----------------------------------------------------------- modificata
SET s.totalSalesWithOptionalProduct = s.totalSalesWithOptionalProduct + cp + tcop,
    s.totalSalesWithoutOptionalProduct = s.totalSalesWithoutOptionalProduct + cp
WHERE s.EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                       FROM service_pack s
                       WHERE s.Id = NEW.Service_pack_id );
END IF;
end //
delimiter ;




-- TO BE TESTED ************************************************

DROP TRIGGER IF EXISTS salesPerPackage_update;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER salesPerPackage_update
    AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
    DECLARE cp,tcop float;
    IF NEW.Isvalid = 1 THEN
SELECT sp.Costpackage, sp.Totalcostoptionalproducts INTO cp,tcop
FROM service_pack sp
WHERE sp.Id = NEW.Service_pack_id;

UPDATE salesPerPackage s
SET s.totalSalesWithOptionalProduct = s.totalSalesWithOptionalProduct + cp + tcop,
    s.totalSalesWithoutOptionalProduct = s.totalSalesWithoutOptionalProduct + cp
WHERE s.EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                       FROM service_pack s
                       WHERE s.Id = NEW.Service_pack_id );
END IF;
end //
delimiter ;
-- ************************************************



-- Average number of optional products sold together with each service package

DROP TABLE IF EXISTS averageOPwithESP;

create table averageOPwithESP
(
 EmployeeServicePack_id int not null
        primary key,
    averageOPs float not null DEFAULT 0,
    totalOPsPerESP int not null DEFAULT 0,
    totalOrdersPerESP int not null DEFAULT 0,
    constraint averageOPs_fk0
        foreign key (EmployeeServicePack_id) references employeeServicePack (Id)
);

DROP TRIGGER IF EXISTS averageOPwithESP_new;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER averageOPwithESP_new
    AFTER INSERT ON employeeServicePack FOR EACH ROW 
    BEGIN
    	INSERT INTO averageOPwithESP(EmployeeServicePack_id)
    	VALUES(NEW.Id);

end //
delimiter ;

DROP TRIGGER IF EXISTS averageOPwithESP_add;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER averageOPwithESP_add
    AFTER INSERT ON `order` FOR EACH ROW 
    BEGIN 
		DECLARE totalOPsPerESP_update integer;
	    IF NEW.isValid = 1 THEN
 			
			
 			SET totalOPsPerESP_update := (	SELECT COUNT(h.Optional_product_id)
 											FROM has h
 											WHERE h.Service_pack_id = NEW.Service_pack_id);
 			UPDATE averageOPwithESP
 			SET totalOPsPerESP = totalOPsPerESP + totalOPsPerESP_update,
            totalOrdersPerESP = totalOrdersPerESP + 1, 
            averageOPs = (totalOPsPerESP + totalOPsPerESP_update)/(totalOrdersPerESP + 1)
 			WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
 											FROM service_pack s
 											WHERE s.Id = NEW.Service_pack_id);
 			
		END IF;
	end //
delimiter ;
   
-- TO BE TESTED ************************************************

DROP TRIGGER IF EXISTS averageOPwithESP_update;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER averageOPwithESP_update
    AFTER UPDATE ON `order` FOR EACH ROW 
    BEGIN 
		DECLARE totalOPsPerESP_update integer;
	    IF NEW.isValid = 1 THEN
 			
 			
			
 			SET totalOPsPerESP_update := (	SELECT COUNT(h.Optional_product_id)
 											FROM has h
 											WHERE h.Service_pack_id = NEW.Service_pack_id);
 			UPDATE averageOPwithESP
 			SET totalOPsPerESP = totalOPsPerESP + totalOPsPerESP_update,
            totalOrdersPerESP = totalOrdersPerESP + 1, 
            averageOPs = (totalOPsPerESP + totalOPsPerESP_update)/(totalOrdersPerESP + 1)
 			WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
 											FROM service_pack s
 											WHERE s.Id = NEW.Service_pack_id);
 			
		END IF;
	end //
delimiter ;
    

-- ************************************************


-- Best Seller: the OP with the highest number of sales in terms of $$
   
DROP TRIGGER IF EXISTS totalSales_update;
DROP TRIGGER IF EXISTS totalSales_add;
DROP TRIGGER IF EXISTS totalSales_new;

DROP TABLE IF EXISTS best_seller_OP;
DROP TABLE IF EXISTS totalSalesPerOP;
DROP TABLE IF EXISTS totalSalesPerOPO;

create table best_seller_OP
(
    Optional_product_id int   not null
        primary key,
    totalSales              float not null,
    constraint best_seller_OP_fk0
        foreign key (Optional_product_id) references optional_product (Id)
);


create table totalSalesPerOPO
(
    Optional_product_id int   not null
        primary key,
    totalSales     float default 0 not null
);



create table totalSalesPerOP
(
    Optional_product_id int             not null,
    totalSales     float default 0 not null
);

delimiter //
CREATE DEFINER  = CURRENT_USER trigger totalSales_add
    after insert
    on `order`
    for each row
begin


    IF NEW.Isvalid = 1 THEN
		DELETE FROM totalSalesPerOPO;
		INSERT INTO totalSalesPerOPO

			SELECT op.Id, (op.Fee * v.Months)
			FROM `order` o
		         JOIN service_pack s on s.Id = o.Service_pack_id
		         JOIN has h on h.Service_pack_id = s.Id
		         JOIN validity_period v on v.Id = s.Validity_period_id
		         JOIN optional_product op on op.Id = h.Optional_product_id
			WHERE s.Id = NEW.Service_pack_id;

		UPDATE totalSalesPerOP s, totalSalesPerOPO op
		SET s.totalSales = s.totalSales + op.totalSales
		WHERE s.Optional_product_id = op.Optional_product_id;


		DELETE FROM best_seller_OP;
		INSERT INTO best_seller_OP
			SELECT s1.Optional_product_id, s1.totalSales
			FROM totalSalesPerOP s1
			WHERE s1.Optional_product_id is not null and s1.totalSales IN (SELECT MAX(s2.totalSales) FROM totalSalesPerOP s2);
	end if;

end //
delimiter ;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger totalSales_update
    after update
    on `order`
    for each row
begin


    IF NEW.Isvalid = 1 THEN
		DELETE FROM totalSalesPerOPO;
		INSERT INTO totalSalesPerOPO

			SELECT op.Id, (op.Fee * v.Months)
			FROM `order` o
		         JOIN service_pack s on s.Id = o.Service_pack_id
		         JOIN has h on h.Service_pack_id = s.Id
		         JOIN validity_period v on v.Id = s.Validity_period_id
		         JOIN optional_product op on op.Id = h.Optional_product_id
			WHERE s.Id = NEW.Service_pack_id;

		UPDATE totalSalesPerOP s, totalSalesPerOPO op
		SET s.totalSales = s.totalSales + op.totalSales
		WHERE s.Optional_product_id = op.Optional_product_id;


		DELETE FROM best_seller_OP;
		INSERT INTO best_seller_OP
			SELECT s1.Optional_product_id, s1.totalSales
			FROM totalSalesPerOP s1
			WHERE s1.Optional_product_id is not null and s1.totalSales IN (SELECT MAX(s2.totalSales) FROM totalSalesPerOP s2);
	end if;

end //
delimiter ;


delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER totalSales_new
    AFTER INSERT ON optional_product FOR EACH ROW BEGIN
    INSERT INTO totalSalesPerOP(Optional_product_id)
    VALUES(NEW.Id);
end //
delimiter ;



-- List of insolvent users,  orders and alert



DROP TABLE IF EXISTS insolvent;

create table insolvent
(
    User_id int not null
    	primary key,
    constraint insolvent_fk0
        foreign key (User_id) references `user` (Id)
);

DROP TRIGGER IF EXISTS insolventUser_update;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger insolventUser_update
    after UPDATE on `user` FOR EACH ROW
BEGIN
    IF NEW.Flag_Ins = 1 THEN
        IF(NEW.Id NOT IN (SELECT User_id FROM insolvent)) THEN
            INSERT INTO insolvent
            VALUES (NEW.Id);
	END IF;
	ELSE
	DELETE FROM insolvent i
	WHERE i.User_id = NEW.Id;
	END IF;
	end //
delimiter ;


DROP TABLE IF EXISTS rejectedOrders;

create table rejectedOrders
(
    Order_id int not null
    	primary key,
    constraint rejectedOrders_fk0
        foreign key (Order_id) references `order`  (Id)
);

DROP TRIGGER IF EXISTS rejectedOrder_new;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger rejectedOrder_new
    AFTER INSERT on `order` FOR EACH ROW
    
BEGIN
    IF(NEW.Isvalid = 0) THEN
        IF(NEW.Id NOT IN (SELECT Order_id FROM rejectedOrders)) THEN
            INSERT INTO rejectedOrders(Order_id)
			VALUES(NEW.Id);
		END IF;
	END IF;
end //
delimiter ;


DROP TRIGGER IF EXISTS rejectedOrder_update;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger rejectedOrder_update
    AFTER UPDATE on `order` FOR EACH ROW
                                   
BEGIN
    IF(NEW.Isvalid = 1) THEN
        IF(NEW.Id IN (SELECT Order_id FROM rejectedOrders)) THEN
           	DELETE FROM rejectedOrders s
			WHERE s.Order_id = NEW.Id;
		END IF;
	END IF;
end //
delimiter ;




DROP TABLE IF EXISTS alerts;

create table alerts
(
    Alert_id int not null
    	primary key,
    constraint Alerts_fk0
        foreign key (Alert_id) references alert (Id)
);


    
DROP TRIGGER IF EXISTS alert_new;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger alert_new
    AFTER INSERT on alert FOR EACH ROW
    
BEGIN
	INSERT INTO alerts
	VALUES (NEW.Id);
end //
delimiter ;




    









