-- Number of total purchases per package
DROP TABLE IF EXISTS numberTotalPurchasesPerESP;

create table numberTotalPurchasesPerESP
(
    EmployeeServicePack_id int not null primary key,
    Numbertotalpurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage foreign key (EmployeeServicePack_id) references employeeServicePack (Id)
);


DROP TRIGGER IF EXISTS addPurchaseToNumberTotalPurchasesPerESP;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addPurchaseToNumberTotalPurchasesPerESP
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    IF NEW.Isvalid = true THEN
UPDATE numberTotalPurchasesPerESP SET Numbertotalpurchases = Numbertotalpurchases + 1
WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                     FROM service_pack s
                     WHERE s.Id = NEW.Service_pack_id);
END IF;
end //
delimiter ;

DROP TRIGGER IF EXISTS createNumberTotalPurchasesPerESP;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createNumberTotalPurchasesPerESP
    AFTER INSERT ON employeeServicePack FOR EACH ROW
BEGIN
INSERT INTO db2Project.numberTotalPurchasesPerESP(EmployeeServicePack_id)
VALUES(NEW.Id);
end //
delimiter ;



-- Number of total purchases per package and validity period

DROP TABLE IF EXISTS numberTotalPurchasesPerESPAndValidityPeriod;

create table numberTotalPurchasesPerESPAndValidityPeriod
(
    EmployeeServicePack_id     int not null,
    Validity_period_id   int not null,
    TotalPurchases int not null DEFAULT 0,
    constraint numberTotalPurchasesPerESPAndValidityPeriod_fk0
        foreign key (EmployeeServicePack_id) references employeeServicePack (Id),
    constraint numberTotalPurchasesPerESPAndValidityPeriod_fk1
        foreign key (Validity_period_id) references validity_period (Id)
);

-- A cosa servono?? ************************************************

create index numberTotalPurchasesPerESPAndValidityPeriod_fk0_idx
    on numberTotalPurchasesPerESPAndValidityPeriod (EmployeeServicePack_id);

create index numberTotalPurchasesPerESPAndValidityPeriod_fk1_idx
    on numberTotalPurchasesPerESPAndValidityPeriod (Validity_period_id); 

-- ************************************************


DROP TRIGGER IF EXISTS createNumberTotalPurchasesPerESPAndValidityPeriod;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createNumberTotalPurchasesPerESPAndValidityPeriod
    AFTER INSERT ON offers FOR EACH ROW BEGIN
    INSERT INTO numberTotalPurchasesPerESPAndValidityPeriod(EmployeeServicePack_id, Validity_period_id)
    VALUES(NEW.EmployeeServicePack_id, NEW.Validity_period_id);

end //
delimiter ;
    
    

DROP TRIGGER IF EXISTS addPurchaseToNumberTotalPurchasesPerESPAndVP;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addPurchaseToNumberTotalPurchasesPerESPAndVP
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.Isvalid = true THEN
UPDATE numberTotalPurchasesPerESPAndValidityPeriod 
SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);
                                     
END IF;
end //
delimiter ;

-- A cosa serve?? ************************************************

DROP TRIGGER IF EXISTS updatePurchaseToNumberTotalPurchasesPerESPAndValidityPeriod;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updatePurchaseToNumberTotalPurchasesPerESPAndValidityPeriod
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
                                   IF NEW.IsValid = true THEN
UPDATE db2Project.numberTotalPurchasesPerESPAndValidityPeriod SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);

END IF;
end //
delimiter ;

--- ************************************************



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


DROP TRIGGER IF EXISTS createESPAddEntryInSalesPerPackage;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createESPAddEntryInSalesPerPackage
    AFTER INSERT ON employeeServicePack FOR EACH ROW BEGIN

    INSERT INTO salesPerPackage(EmployeeServicePack_id)
    VALUES(NEW.Id);

end //
delimiter ;



DROP TRIGGER IF EXISTS addSalesPerPackage;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addSalesPerPackage
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    DECLARE cp,tcop float;
    IF NEW.Isvalid = 1 THEN
SET cp := 	(SELECT sp.Costpackage
			FROM service_pack sp
			WHERE sp.Id = NEW.Service_pack_id);
			
SET tcop := 	(SELECT sp.Totalcostoptionalproducts
				FROM service_pack sp
				WHERE sp.Id = NEW.Service_pack_id);


UPDATE salesPerPackage
SET totalSalesWithOptionalProduct = totalSalesWithOptionalProduct + cp + tcop,
    totalSalesWithoutOptionalProduct = totalSalesWithoutOptionalProduct + cp
WHERE EmployeeServicePack_id IN (SELECT s.Service_pack_employee_id
                       FROM service_pack s
                       WHERE s.Id = NEW.Service_pack_id );
END IF;
end //
delimiter ;




-- A cosa serve?? ************************************************

DROP TRIGGER IF EXISTS updateSalesPerPackage;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updateSalesPerPackage
    AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
    DECLARE cp,tcop float;
    IF NEW.Isvalid = true THEN
SELECT sp.valuePackage, sp.totalValueOptionalProducts INTO cp,tcop
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
--- ************************************************



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

DROP TRIGGER IF EXISTS averageOPwithESP_update;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER averageOPwithESP_update
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
    

--


-- Best Seller: the OP with the highest number of sales in terms of $$

DROP TABLE IF EXISTS best_seller_OP;
DROP TABLE IF EXISTS totalSalesPerOP;

create table best_seller_OP
(
 Optional_product_id int not null
        primary key,
    constraint bs_fk0
        foreign key (Optional_product_id) references optional_product (Id)
);

create table totalSalesPerOP
(
 Optional_product_id int not null
        primary key,
    totalSales float not null DEFAULT 0,
   
    constraint totalSalesPerOP_fk1
        foreign key (Optional_product_id) references optional_product (Id)
);

DROP TRIGGER IF EXISTS BestSellerOP_new;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER BestSellerOP_new
	AFTER INSERT ON optional_product FOR EACH ROW 
	    BEGIN
		    DECLARE massimo integer;
		    SET massimo := (SELECT *
		    				FROM best_seller_OP 
		    				GROUP BY Optional_product_id
		    				LIMIT 1);
		    INSERT INTO totalSalesPerOP(Optional_product_id)
	    		VALUES(NEW.Id);
		    IF massimo IS NULL OR massimo = '' THEN
	    		
	    		INSERT INTO best_seller_OP(Optional_product_id)
	    		VALUES(NEW.Id);

	    	END IF;
	    	
	end //
delimiter ;
-- Funzionano
DROP TRIGGER IF EXISTS BestSellerOP_update;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER BestSellerOP_update
	AFTER INSERT ON `has` FOR EACH ROW 
	    BEGIN
		    DECLARE newMaxVal float;
		    DECLARE newMaxId integer;
		    
		    	BEGIN
			    	DECLARE op_new integer;
			    	DECLARE newSale float;
			    	
			    	SET op_new := (	SELECT h.Optional_product_id as Id
			    					FROM has h
			    					WHERE h.Service_pack_id = NEW.Service_pack_id
			    					LIMIT 1);
			    	SET newSale := (SELECT op.Fee
			    					FROM optional_product op
			    					WHERE op.Id = op_new);
			    	UPDATE totalSalesPerOP
			    	SET totalSales = totalSales + newSale
			    	WHERE Optional_product_id = op_new;
			    END;
				
			    SET newMaxVal :=(SELECT MAX(totalSales) as m
			    				FROM totalSalesPerOP tot
			    				GROUP BY tot.Optional_product_id
			    				LIMIT 1);
			    SET newMaxId :=(SELECT tot.Optional_product_id as Id
			    				FROM totalSalesPerOP tot
			    				WHERE tot.totalSales = newMaxVal
			    				LIMIT 1);
			    								
			    UPDATE totalSalesPerOP
			    SET Optional_product_id = newMaxId
			    WHERE Optional_product_id != newMaxId;
		    
		    
end //
delimiter ;
--
'2','3','1','2018-02-03 07:30:00','1','1'

-- List of insolvent users, suspended orders and alert

	-- INSOLVENT USERS ---> Dovrebbe funzionare, problema con value BLOB

DROP TABLE IF EXISTS insolvent;

create table insolvent
(
    User_id int not null
    	primary key,
    constraint insolvent_fk0
        foreign key (User_id) references `user` (Id)
);

DROP TRIGGER IF EXISTS updateInsolventUser;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateInsolventUser
    after UPDATE on `user` FOR EACH ROW
BEGIN
    IF NEW.Flag_Ins = true THEN
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
    IF(NEW.Isvalid = false) THEN
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
    IF(NEW.Isvalid = true) THEN
        IF(NEW.Id IN (SELECT Order_id FROM suspendedOrders)) THEN
           	DELETE FROM suspendedOrders s
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
    constraint alerts_fk0
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




    









