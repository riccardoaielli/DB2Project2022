package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "employeeServicePackEntity")
@NamedQueries({
        //@NamedQuery(name = "EmployeeEntity.checkCredentials", query = "SELECT a FROM EmployeeEntity a WHERE a.username = :username AND a.password = :password")
})

   

public class EmployeeServicePackEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;
	 
	 @OneToMany(mappedBy = "service_pack_id", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
	    private List<ServicePackageEntity> servicePacks = new ArrayList<>();
	 
	 @ManyToOne
	    @JoinColumn(name = "ValidityP_fk", nullable = false)  // unidirectional, order has the fk of the user who created it 
	    private ValidityPeriodEntity validityP_fk;
}
