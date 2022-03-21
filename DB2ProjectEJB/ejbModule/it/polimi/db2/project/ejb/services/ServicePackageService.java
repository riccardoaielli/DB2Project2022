package it.polimi.db2.project.ejb.services;

import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
import it.polimi.db2.project.ejb.entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;

    public List<ServicePackageEntity> findAllServices() {
        return em.createNamedQuery("ServicePackageEntity.findAll", ServicePackageEntity.class)
                .getResultList();
    }
    
//    public List<ServicePackageEntity> findServicePackById() {
//        return em.createNamedQuery("ServicePackageEntity.findById", ServicePackageEntity.class)
//                .getResultList();
//    }
    
    public ServicePackageEntity findServicePackById(int id) {
        return em.createNamedQuery("ServicePackageEntity.findById", ServicePackageEntity.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    public ServicePackageEntity createServicePackage(ServicePackageEntity servicePackage) throws SQLException {
        try {
            em.persist(servicePackage);
            em.flush();
            return servicePackage;
        } catch (ConstraintViolationException ignored) {
            return null;
        }
    }

    
}
