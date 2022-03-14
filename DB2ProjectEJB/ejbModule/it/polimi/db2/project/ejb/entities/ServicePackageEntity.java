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
    
    @ManyToOne
    @JoinColumn(name="Validity_period_id", nullable = false) // owner della relazione associate
    private ValidityPeriodEntity validity_period_id;
    
	@ManyToMany
	@JoinTable(name = "has", joinColumns = @JoinColumn(name = "Service_pack_id"), inverseJoinColumns = @JoinColumn(name = "Optional_product_id"))
	private List<OptionalProductEntity> optionalProductEntities; // owner of the relation has
	
//	@ManyToMany
//	@JoinTable(name = "comprises", joinColumns = @JoinColumn(name = "Service_pack_id"), inverseJoinColumns = @JoinColumn(name = "Service_id"))
//	private List<ServiceEntity> serviceEntities; // owner of the relation
    
	@OneToOne(mappedBy = "service_pack_id", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
	private OrderEntity orders; // relazione in
	
	@ManyToOne
    @JoinColumn(name="Service_pack_employee_id", nullable = false)
    private EmployeeServicePackEntity service_pack_employee_id; // owner della relazione made_of
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
      
}
