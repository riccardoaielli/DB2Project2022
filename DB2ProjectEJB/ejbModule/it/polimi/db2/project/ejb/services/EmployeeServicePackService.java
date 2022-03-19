package it.polimi.db2.project.ejb.services;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.HTMLDocument.Iterator;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.ServiceEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;

@Stateless
public class EmployeeServicePackService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	@EJB(name = "it.polimi.db2.project.ejb.services/ServiceService")
    private ServiceService servService;
	
    public List<EmployeeServicePackEntity> findAllEmployeeServicePack(){
        return em.createNamedQuery("EmployeeServicePack.findAll", EmployeeServicePackEntity.class).getResultList();
    }
    
    public EmployeeServicePackEntity findEmployeeServicePackByName(String name) {
        return em.createNamedQuery("EmployeeServicePack.findByName", EmployeeServicePackEntity.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    public EmployeeServicePackEntity findEmployeeServicePackById (int id) {
        return em.createNamedQuery("EmployeeServicePack.findById", EmployeeServicePackEntity.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
	
	public EmployeeServicePackEntity addNewEmployeeServicePack(String name, List<String> servicesStrings) throws CredentialsException {
//        if (this.findEmployeeServicePackByName(name) != null) {
//            throw new CredentialsException("Name for the service pack already in use!");
//        }
       
        
        List<ServiceEntity> services = new ArrayList<>();  // where I am going to insert the service entitites to pass to the Employee service pack constructor
        ServiceEntity servEntity = null;
        
        for (String serv : servicesStrings){
        	servEntity = servService.findServiceByName(serv);
        	System.out.println("OK: " + serv);
        	System.out.println("OK: " + servEntity);
        	if( servEntity != null ) {
        		services.add(servEntity);
        	}
        }
        EmployeeServicePackEntity newESP = new EmployeeServicePackEntity(name, services);
        em.persist(newESP);

        return newESP;
    }

}
