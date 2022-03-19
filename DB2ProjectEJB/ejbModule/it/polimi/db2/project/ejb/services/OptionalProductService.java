package it.polimi.db2.project.ejb.services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.project.ejb.entities.OptionalProductEntity;

@Stateless
public class OptionalProductService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	public List<OptionalProductEntity> findOptProdOfEmployeeServicePackId(int employeeServicePack_id){
        return em.createNamedQuery("OptionalProduct.findOptProdOfEmployeeServicePackId", OptionalProductEntity.class)
                .setParameter("employeeServicePack_id", employeeServicePack_id)
                .getResultList();
    }
	
	public Optional<OptionalProductEntity> findByOptProdID(int optionalProduct_id) {
        return em.createNamedQuery("OptionalProduct.findByID", OptionalProductEntity.class)
            .setParameter("optionalProduct_id", optionalProduct_id)
            .getResultStream().findFirst();
    }
	
	public OptionalProductEntity findByOptProdName(String optionalProduct_name) {
        return em.createNamedQuery("OptionalProduct.findByName", OptionalProductEntity.class)
            .setParameter("optionalProduct_name", optionalProduct_name)
            .setMaxResults(1)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }

}
