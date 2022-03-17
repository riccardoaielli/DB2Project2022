package it.polimi.db2.project.ejb.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.project.ejb.entities.ServiceEntity;
import it.polimi.db2.project.ejb.entities.ServicePackageEntity;

@Stateless
public class ServiceService {
	
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	public ServiceEntity findServiceByName(String name) {
        return em.createNamedQuery("ServiceEntity.findServiceByName", ServiceEntity.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
