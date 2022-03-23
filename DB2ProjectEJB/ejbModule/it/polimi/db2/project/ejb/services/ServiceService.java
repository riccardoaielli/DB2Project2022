package it.polimi.db2.project.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
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
	
	public List<ServiceEntity> findAllService() {
        return em.createNamedQuery("ServiceEntity.findAll", ServiceEntity.class).getResultList();
                
    }
	
	
	
	public ServiceEntity findServiceById(Integer id) {
        return em.createNamedQuery("ServiceEntity.findServiceById", ServiceEntity.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
