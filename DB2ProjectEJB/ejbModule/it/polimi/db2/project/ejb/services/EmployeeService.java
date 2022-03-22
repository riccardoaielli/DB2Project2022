package it.polimi.db2.project.ejb.services;

import it.polimi.db2.project.ejb.SalesReportEntities.AverageOPwithESP;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESP;
import it.polimi.db2.project.ejb.SalesReportEntities.NumberTotalPurchasesPerESPAndValidityPeriod;
import it.polimi.db2.project.ejb.SalesReportEntities.SalesPerPackage;
import it.polimi.db2.project.ejb.entities.*;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;

    
    public EmployeeEntity checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<EmployeeEntity> uList;

        try {
            uList = em.createNamedQuery("EmployeeEntity.checkCredentials", EmployeeEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }
    
    
    
}
