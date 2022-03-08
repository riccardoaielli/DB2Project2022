package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_pack")
//@NamedQueries({
//        @NamedQuery(name = "ProductEntity.findAll", query = "SELECT p FROM ProductEntity p"),
//        @NamedQuery(name = "ProductEntity.findByDate", query = "SELECT p FROM ProductEntity p INNER JOIN p.questionnaires q WHERE q.date = :date"),
//})
public class ServicePackageEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

	@Column(name = "Name", nullable = false, length = 45)
    private String name;
    
    @Column(name = "Optional_products", nullable = false)
    private int optional_products;
    
    @Column(name = "Services", nullable = false)
    private int services;
    
    @ManyToOne
    @JoinColumn(name="Validity_period", nullable = false)
    private ValidityPeriodEntity validity_period;
    
	@ManyToMany
	@JoinTable(name = "has", joinColumns = @JoinColumn(name = "Service_pack_id"), inverseJoinColumns = @JoinColumn(name = "Optional_product_id"))
	private List<OptionalProductEntity> optionalProductEntities; // owner of the relation
    
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

	public int getOptional_products() {
		return optional_products;
	}

	public void setOptional_products(int optional_products) {
		this.optional_products = optional_products;
	}

	public int getServices() {
		return services;
	}

	public void setServices(int services) {
		this.services = services;
	}
      
}
