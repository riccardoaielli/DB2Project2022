package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "employeeServicePack")
@NamedQueries({
        //@NamedQuery(name = "EmployeeEntity.checkCredentials", query = "SELECT a FROM EmployeeEntity a WHERE a.username = :username AND a.password = :password")
})

   

public class EmployeeServicePackEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;
	 
	 @Column(name = "Name", nullable=false)
	    private String name;
	 
	 @OneToMany(mappedBy = "service_pack_employee_id", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
	    private List<ServicePackageEntity> servicePacks = new ArrayList<>(); // relation made_of
	 
	 @ManyToMany
	 @JoinTable(name = "offers", joinColumns = @JoinColumn(name = "EmployeeServicePack_id"), inverseJoinColumns = @JoinColumn(name = "Validity_period_id"))
	 private List<ValidityPeriodEntity> validityPeriodEntity; // owner of the relation offers
	 
	 @ManyToMany
	 @JoinTable(name = "comprises", joinColumns = @JoinColumn(name = "EmployeeServicePack_id"), inverseJoinColumns = @JoinColumn(name = "Service_id"))
	 private List<ServiceEntity> serviceEntities; // owner of the relation comprises
}
