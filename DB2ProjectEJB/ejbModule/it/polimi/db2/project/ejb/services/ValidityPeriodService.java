package it.polimi.db2.project.ejb.services;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.entities.*;


@Stateless
public class ValidityPeriodService {
	
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	public List<ValidityPeriodEntity> findAllValidityPeriod() {
        return em.createNamedQuery("ValidityPeriodEntity.findAllValidityPeriod", ValidityPeriodEntity.class).getResultList();
    }
	
	public ValidityPeriodEntity findValidityPeriodById(Integer id) {
        return em.createNamedQuery("ValidityPeriodEntity.findValidityPeriodById", ValidityPeriodEntity.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
	
	public List<ValidityPeriodEntity> findValidityPeriodsOfEmployeeServicePackId(int employeeServicePack_id ){
        return em.createNamedQuery("ValidityPeriod.findValidityPeriodsByEmployeeServicePack", ValidityPeriodEntity.class)
            .setParameter("employeeServicePack_id", employeeServicePack_id)
            .getResultList();
    }
	
	public ValidityPeriodEntity findByValPeriodID(int validityPeriod_id) {
        return em.createNamedQuery("ValidityPeriod.findByID", ValidityPeriodEntity.class)
            .setParameter("validityPeriod_id", validityPeriod_id)
            .setMaxResults(1)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }
}
