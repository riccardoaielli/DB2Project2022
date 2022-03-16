-- Number of total purchases per package

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


DROP TRIGGER IF EXISTS updatePurchaseToNumberTotalPurchasesPerESP;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updatePurchaseToNumberTotalPurchasesPerESP
    AFTER UPDATE ON `order` FOR EACH ROW
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

DROP TABLE IF EXISTS numberTotalPurchasesPerESP;

create table numberTotalPurchasesPerESP
(
    EmployeeServicePack_id int not null primary key,
    Numbertotalpurchases int default 0 not null,
    constraint numberOfTotalPurchasesPerPackage foreign key (EmployeeServicePack_id) references employeeServicePack (Id)
);

-- Number of total purchases per package and validity period

DROP TRIGGER IF EXISTS addPurchaseToNumberTotalPurchasesPerESPAndVP;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addPurchaseToNumberTotalPurchasesPerESPAndVP
    AFTER INSERT ON `order` FOR EACH ROW BEGIN
    IF NEW.Isvalid = true THEN
UPDATE db2Project.numberTotalPurchasesPerESPAndValidityPeriod SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);
                                     
END IF;
end //
delimiter ;


DROP TRIGGER IF EXISTS updatePurchaseToNumberTotalPurchasesPerESPAndValidityPeriod;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER updatePurchaseToNumberTotalPurchasesPerESPAndValidityPeriod
    AFTER UPDATE ON `order` FOR EACH ROW BEGIN
                                   IF NEW.IsValid = true THEN
UPDATE db2Project.numberTotalPurchasesPerESPAndValidityPeriod SET TotalPurchases = TotalPurchases + 1
WHERE (EmployeeServicePack_id, Validity_period_id) IN (SELECT s.Service_pack_employee_id, s.Validity_period_id
                                     FROM db2Project.service_pack s
                                     WHERE s.Id = NEW.Service_pack_id);

END IF;
end //
delimiter ;


DROP TRIGGER IF EXISTS createNumberTotalPurchasesPerESPAndValidityPeriod;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createNumberTotalPurchasesPerESPAndValidityPeriod
    AFTER INSERT ON offers FOR EACH ROW BEGIN
    INSERT INTO numberTotalPurchasesPerESPAndValidityPeriod(EmployeeServicePack_id, Validity_period_id)
    VALUES(NEW.EmployeeServicePack_id, NEW.Validity_period_id);

end //
delimiter ;


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

create index numberTotalPurchasesPerESPAndValidityPeriod_fk0_idx
    on numberTotalPurchasesPerESPAndValidityPeriod (EmployeeServicePack_id);

create index numberTotalPurchasesPerESPAndValidityPeriod_fk1_idx
    on numberTotalPurchasesPerESPAndValidityPeriod (Validity_period_id);

-- Total value of sales per package with and without the optional products

DROP TRIGGER IF EXISTS addSalesPerPackage;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER addSalesPerPackage
    AFTER INSERT ON `order` FOR EACH ROW
BEGIN
    DECLARE cp,tcop float;
    IF NEW.Isvalid = true THEN
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


DROP TRIGGER IF EXISTS createESPAddEntryInSalesPerPackage;

delimiter //
CREATE DEFINER  = CURRENT_USER TRIGGER createESPAddEntryInSalesPerPackage
    AFTER INSERT ON employeeServicePack FOR EACH ROW BEGIN

    INSERT INTO salesPerPackage(EmployeeServicePack_id)
    VALUES(NEW.Id);

end //
delimiter ;


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

-- Average number of optional products sold together with each service package


--


-- List of insolvent users, suspended orders and alert

DROP TRIGGER IF EXISTS addAlert;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger addAlert
    after insert
    on alert
    for each row
begin
INSERT INTO alerts
VALUES (NEW.Id);
end //
delimiter ;

DROP TABLE IF EXISTS alerts;

create table alerts
(
    Alert_id int not null,
    constraint alerts_fk0
        foreign key (Alert_id) references alert (Id)
);

create index alerts_fk0_idx
    on alerts (Alert_id);
    


DROP TRIGGER IF EXISTS updateInsolventUser;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateInsolventUser
    after update
		on `user`
			for each row
begin
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

DROP TABLE IF EXISTS insolvent;

create table insolvent
(
    User_id int not null,
    constraint insolvent_fk0
        foreign key (User_id) references `user` (Id)
);

create index insolvent_fk0_idx
    on insolvent (User_id);


DROP TRIGGER IF EXISTS addSuspendedOrder;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger addSuspendedOrder
    after insert
    on `order`
    for each row
begin
    IF(NEW.Isvalid = false) THEN
        IF(NEW.Id NOT IN (SELECT Order_id FROM suspendedOrders)) THEN
            INSERT INTO suspendedOrders(Order_id)
			VALUES(NEW.Id);
		END IF;
	ELSE
	IF(NEW.Id IN (SELECT Order_id FROM suspendedOrders)) THEN
DELETE FROM suspendedOrders s
WHERE s.Order_id = NEW.Id;
END IF;
END IF;
end //
delimiter ;

DROP TRIGGER IF EXISTS updateSuspendedOrder;

delimiter //
CREATE DEFINER  = CURRENT_USER trigger updateSuspendedOrder
    after update
                                   on `order`
                                   for each row
begin
    IF(NEW.Isvalid = false) THEN
        IF(NEW.Id NOT IN (SELECT Order_id FROM suspendedOrders)) THEN
            INSERT INTO suspendedOrders(Order_id)
			VALUES(NEW.Id);
END IF;
ELSE
        IF(NEW.Id IN (SELECT Order_id FROM suspendedOrders)) THEN
DELETE FROM suspendedOrders s
WHERE s.Order_id = NEW.Id;
END IF;
END IF;

end //
delimiter ;

DROP TABLE IF EXISTS suspendedOrders;

create table suspendedOrders
(
    Order_id int not null,
    constraint suspendedOrders_fk0
        foreign key (Order_id) references `order`  (Id)
);

create index suspendedOrders_fk0_idx
    on suspendedOrders (Order_id);


