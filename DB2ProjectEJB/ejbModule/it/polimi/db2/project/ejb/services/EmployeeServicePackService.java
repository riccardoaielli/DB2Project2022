package it.polimi.db2.project.ejb.services;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.HTMLDocument.Iterator;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.OptionalProductEntity;
import it.polimi.db2.project.ejb.entities.ServiceEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;
import it.polimi.db2.project.ejb.exceptions.CredentialsException;

@Stateless
public class EmployeeServicePackService {
	@PersistenceContext(unitName = "DB2ProjectEJB")
    private EntityManager em;
	
	@EJB(name = "it.polimi.db2.project.ejb.services/ServiceService")
    private ServiceService servService;
	
	@EJB(name = "it.polimi.db2.project.ejb.services/ValidityPeriodService")
    private ValidityPeriodService vpService;
	
	@EJB(name = "it.polimi.db2.project.ejb.services/OptionalProductService")
    private OptionalProductService opService;
	
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
	
	public EmployeeServicePackEntity addNewEmployeeServicePack(String name, List<String> servicesStrings,  List<String> ValidityPIds, List<String> OptionalPStrings) throws NonUniqueResultException {
		
		
		if (findEmployeeServicePackByName(name) != null) {
        	
            return null;
            
        }
       
		
        List<ServiceEntity> services = new ArrayList<>();  // where I am going to insert the service entitites to pass to the Employee service pack constructor
        List<ValidityPeriodEntity> vps = new ArrayList<>();
        List<OptionalProductEntity> ops = new ArrayList<>();
        
        ServiceEntity servEntity = null;
        ValidityPeriodEntity vpEntity = null;
        OptionalProductEntity opEntity = null;
       
        for (String serv : servicesStrings){
        	servEntity = servService.findServiceByName(serv);
    
        	if( servEntity != null ) {
        		services.add(servEntity);
        	}
        }
        
        for (String vp : ValidityPIds){
        	
        	vpEntity = vpService.findValidityPeriodById(Integer.parseInt(vp));
    
        	if( vpEntity != null ) {
        		vps.add(vpEntity);
        	}
        }
        
        for (String op : OptionalPStrings){
        	opEntity = opService.findOptionalProductByName(op);
    
        	if( opEntity != null ) {
        		ops.add(opEntity);
        	}
        }
        
        
        EmployeeServicePackEntity newESP = new EmployeeServicePackEntity(name, services, vps, ops);
        em.persist(newESP);

        return newESP;
    }

}
