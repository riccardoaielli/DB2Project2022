package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Entity
@Table(name = "optional_product")
@NamedQueries({
        @NamedQuery(name = "OptionalProductEntity.findAllOptionalProduct", query = "SELECT op FROM OptionalProductEntity op"),
        @NamedQuery(name = "OptionalProductEntity.findByName", query = "SELECT op FROM OptionalProductEntity op WHERE op.name = :name"),
        @NamedQuery(name = "OptionalProductEntity.findAssociatedESP", query = "SELECT esp FROM OptionalProductEntity esp WHERE esp.employeeServicePackEntity = :name"),
        @NamedQuery(
                name = "OptionalProduct.findByID",
                query = "SELECT o FROM OptionalProductEntity o " +
                        "WHERE o.id = :optionalProduct_id"
        ),
    	
    	@NamedQuery(
                name = "OptionalProduct.findByName",
                query = "SELECT o FROM OptionalProductEntity o " +
                        "WHERE o.name = :optionalProduct_name"
        ),
    	
    	@NamedQuery(
    	        name = "OptionalProduct.findOptProdOfEmployeeServicePackId",
    	        query = "SELECT o FROM OptionalProductEntity o " +
    	                "JOIN o.employeeServicePackEntity s " +
    	                "WHERE s.id = :employeeServicePack_id "
    	)
})

public class OptionalProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
    
    @Column(name = "Name", nullable = false, length = 45)
    private String name;

    @Column(name = "Fee", nullable = false)
    private float fee;
    
    @ManyToMany(mappedBy = "optionalProductEntities") // relazione has
    private Collection<ServicePackageEntity> servicePackageEntities;
    
    @ManyToMany(mappedBy = "optionalProductEntity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE}) // relation propose
    private List<EmployeeServicePackEntity> employeeServicePackEntity = new ArrayList<>();
    
   

	public OptionalProductEntity(String name, float fee) {
	
		this.name = name;
		this.fee = fee;
	}
	public OptionalProductEntity() {}

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

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}


//
//    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
//    private List<QuestionnaireEntity> questionnaires = new ArrayList<>();

}
