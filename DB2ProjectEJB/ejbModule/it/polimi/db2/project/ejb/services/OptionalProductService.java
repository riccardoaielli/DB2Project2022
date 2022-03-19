
import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.ServiceEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
    
    public OptionalProductEntity findOptionalProductByName(String name) {
        return em.createNamedQuery("OptionalProductEntity.findByName", OptionalProductEntity.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    public List<OptionalProductEntity> findAllOptionalProduct() {
        return em.createNamedQuery("OptionalProductEntity.findAllOptionalProduct", OptionalProductEntity.class).getResultList();
                
    }
    
    public OptionalProductEntity findOptionalProductAssociatedESP(String name) {
        return em.createNamedQuery("OptionalProductEntity.findOptionalProductAssociatedESP", OptionalProductEntity.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public OptionalProductEntity addNewOptionalProduct(String name, float fee) throws NonUniqueResultException {
		
		
		if (findOptionalProductByName(name) != null)
            return null;
           
		OptionalProductEntity newOP = new OptionalProductEntity(name, fee);
        em.persist(newOP);

        return newOP;
    }
    
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
<<<<<<< HEAD
=======

>>>>>>> refs/heads/buybranch
}
