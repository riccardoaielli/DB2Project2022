package it.polimi.db2.project.ejb.services;

import it.polimi.db2.project.ejb.entities.ServicePackageEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;

    public List<ServicePackageEntity> findAllServices() {
        return em.createNamedQuery("ServicePackageEntity.findAll", ServicePackageEntity.class)
                .getResultList();
    }

    
}
