package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "employeeServicePack", schema = "db2Project")

@NamedQueries({
	@NamedQuery(
	        name = "EmployeeServicePack.findById",
	        query = "SELECT esp FROM EmployeeServicePackEntity esp WHERE esp.id = :id"),
	
	@NamedQuery(
	        name = "EmployeeServicePack.findByName",
	        query = "SELECT esp FROM EmployeeServicePackEntity esp WHERE esp.name = :name"),
	        
	@NamedQuery(
	        name = "EmployeeServicePack.findAll",
	        query = "SELECT esp FROM EmployeeServicePackEntity esp")
})
   

public class EmployeeServicePackEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;
	 
	 @Column(name = "Name", nullable=false)
	    private String name;
	 
	 @OneToMany(mappedBy = "service_pack_employee_id", fetch = FetchType.EAGER)
	    private List<ServicePackageEntity> servicePacks; // relation made_of
	 
	 @ManyToMany(fetch=FetchType.EAGER)
	 @JoinTable(name = "offers", joinColumns = @JoinColumn(name = "EmployeeServicePack_id"), inverseJoinColumns = @JoinColumn(name = "Validity_period_id"))
	 private List<ValidityPeriodEntity> validityPeriodEntity; // owner of the relation offers
	
	 
	 @ManyToMany(fetch=FetchType.EAGER)
	 @JoinTable(name = "propose", joinColumns = @JoinColumn(name = "EmployeeServicePack_id"), inverseJoinColumns = @JoinColumn(name = "Optional_product_id"))
	 private List<OptionalProductEntity> optionalProductEntity; // owner of the relation propose
	 
	 @ManyToMany(fetch=FetchType.EAGER)
	 @JoinTable(name = "comprises", joinColumns = @JoinColumn(name = "EmployeeServicePack_id"), inverseJoinColumns = @JoinColumn(name = "Service_id"))
	 private List<ServiceEntity> serviceEntities; // owner of the relation comprises

	 public EmployeeServicePackEntity(String name, List<ServiceEntity> services, List<ValidityPeriodEntity> vp, List<OptionalProductEntity> op) {
			this.name =  name;
			this.serviceEntities = services;
			this.validityPeriodEntity = vp;
			this.optionalProductEntity = op;
		}
	 
	 public EmployeeServicePackEntity() {}
	 
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServicePackageEntity> getServicePacks() {
		return servicePacks;
	}

	public void setServicePacks(List<ServicePackageEntity> servicePacks) {
		this.servicePacks = servicePacks;
	}

	public List<ValidityPeriodEntity> getValidityPeriodEntity() {
		return validityPeriodEntity;
	}

	public void setValidityPeriodEntity(List<ValidityPeriodEntity> validityPeriodEntity) {
		this.validityPeriodEntity = validityPeriodEntity;
	}

	public List<ServiceEntity> getServiceEntities() {
		return serviceEntities;
	}

	public void setServiceEntities(List<ServiceEntity> serviceEntities) {
		this.serviceEntities = serviceEntities;
	}
	 
	 
}
