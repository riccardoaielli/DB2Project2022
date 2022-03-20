package it.polimi.db2.project.ejb.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import it.polimi.db2.project.ejb.entities.AlertEntity;

@Stateless
public class AlertService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	public void createAlert(AlertEntity alert){
        try {
            em.persist(alert);
            em.flush();
        } catch (ConstraintViolationException ignored) {
        }
    }

}
