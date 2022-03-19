package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Table(name = "service_pack")
@NamedQueries({
    @NamedQuery(name = "ServicePackageEntity.findAll", query = "SELECT sp FROM ServicePackageEntity sp"),
    
})

@Entity
public class ServicePackageEntity {
	
	public ServicePackageEntity(){
    }
	
	public ServicePackageEntity(EmployeeServicePackEntity service_pack_employee_id, ValidityPeriodEntity validity_period_id, Date start_date, Date deactivation_date, float costpackage,
            float totalcostoptionalproducts, List<OptionalProductEntity> optionalProductEntities) {
		
		this.start_date = start_date;
		this.deactivation_date = deactivation_date;
		this.costpackage = costpackage;
		this.totalcostoptionalproducts = totalcostoptionalproducts;
        this.service_pack_employee_id = service_pack_employee_id;
		this.validity_period_id = validity_period_id;
		this.optionalProductEntities = optionalProductEntities;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
	
	@Column(name = "Start_date", nullable = false)
    private Date start_date;
	
	@Column(name = "Deactivation_date", nullable = false)
    private Date deactivation_date;
	
	@Column(name = "Costpackage", unique=true, nullable=false)
    private float costpackage;

    @Column(name = "Totalcostoptionalproducts", unique=true, nullable=false)
    private float totalcostoptionalproducts;
    
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
