package it.polimi.db2.project.ejb.services;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import it.polimi.db2.project.ejb.SalesReportEntities.Alert;
import it.polimi.db2.project.ejb.SalesReportEntities.AverageOPwithESP;
import it.polimi.db2.project.ejb.SalesReportEntities.Best_seller_OP;
import it.polimi.db2.project.ejb.SalesReportEntities.Insolvent;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESP;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESPAndValidityPeriod;
import it.polimi.db2.project.ejb.SalesReportEntities.RejectedOrder;
import it.polimi.db2.project.ejb.SalesReportEntities.SalesPerPackage;
import it.polimi.db2.project.ejb.entities.OrderEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;

@Stateless
public class SalesReportService {
	 @PersistenceContext(unitName = "DB2ProjectEJB")
	    private EntityManager em;
	 
	public List<SalesPerPackage> findAllSalesPerPackage() {
    	return em.createNamedQuery("SalesPerPackage.findAll", SalesPerPackage.class)
                .getResultList();
    }
    public List<AverageOPwithESP> findAllAverageOPwithESP() {
    	return em.createNamedQuery("AverageOPwithESP.findAll", AverageOPwithESP.class)
                .getResultList();
    }
    public List<NumberTotalPurchasesPerESP> findAllNumberTotalPurchasesPerESP() {
    	return em.createNamedQuery("NumberTotalPurchasesPerESP.findAll", NumberTotalPurchasesPerESP.class)
                .getResultList();
    }
    public List<NumberTotalPurchasesPerESPAndValidityPeriod> findAllNumberTotalPurchasesPerESPAndValidityPeriod() {
    	return em.createNamedQuery("NumberTotalPurchasesPerESPAndValidityPeriod.findAll", NumberTotalPurchasesPerESPAndValidityPeriod.class)
                .getResultList();
    }
    public List<Alert> findAllAlert() {
    	return em.createNamedQuery("Alert.findAll", Alert.class)
                .getResultList();
    }
    public List<Insolvent> findAllInsolvent() {
    	return em.createNamedQuery("Insolvent.findAll", Insolvent.class)
                .getResultList();
    }
    public List<RejectedOrder> findAllRejectedOrder() {
    	return em.createNamedQuery("RejectedOrder.findAll", RejectedOrder.class)
                .getResultList();
    }
    
    public Best_seller_OP findAllBest_seller_OP() {
    	return em.createNamedQuery("Best_seller_OP.findAll", Best_seller_OP.class)
    			.getResultStream().findFirst().orElse(null);
    }
    
    public void refreshAll() {
    	em.getEntityManagerFactory().getCache().evictAll();    }
}
