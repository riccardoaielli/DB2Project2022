package it.polimi.db2.project.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}