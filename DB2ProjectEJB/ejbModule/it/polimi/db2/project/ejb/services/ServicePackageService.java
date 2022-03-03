//package it.polimi.db2.project.ejb.services;
//
//import it.polimi.db2.project.ejb.entities.ServicePackageEntity;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.sql.Date;
//import java.util.List;
//
//@Stateless
//public class ServicePackageService {
//    @PersistenceContext(unitName = "db2Project")
//    private EntityManager em;
//
////    public List<ServicePackageEntity> findAllProducts() {
////        return em.createNamedQuery("ServicePackageEntity.findAll", ServicePackageEntity.class)
////                .getResultList();
////    }
////
////    public ServicePackageEntity findProductByDay(Date date) {
////        return em.createNamedQuery("ServicePackageEntity.findByDate", ServicePackageEntity.class)
////                .setParameter("date", date)
////                .setMaxResults(1)
////                .getResultStream()
////                .findFirst()
////                .orElse(null);
////    }
//}
