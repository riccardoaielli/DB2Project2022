package it.polimi.db2.project.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;

@Stateless
public class EmployeeServicePackService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
    public List<EmployeeServicePackEntity> findAllEmployeeServicePack(){
        return em.createNamedQuery("EmployeeServicePack.findAll", EmployeeServicePackEntity.class).getResultList();
    }

}
