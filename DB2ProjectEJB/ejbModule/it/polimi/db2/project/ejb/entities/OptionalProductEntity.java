package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "optional_product")
//@NamedQueries({
//        @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
//        @NamedQuery(name = "ProductEntity.findByDate", query = "SELECT p FROM ProductEntity p INNER JOIN p.questionnaires q WHERE q.date = :date"),
//})

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
