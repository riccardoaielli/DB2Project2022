package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Table(name = "service_pack", schema="db2Project")
@NamedQueries({ 
	@NamedQuery(name = "ServicePackageEntity.findAll", query = "SELECT sp FROM ServicePackageEntity sp"),
	@NamedQuery(name = "ServicePackageEntity.findById", query = "SELECT sp FROM ServicePackageEntity sp WHERE sp.id = :id"),

})

@Entity
public class ServicePackageEntity {

	public ServicePackageEntity() {
	}

	public ServicePackageEntity(EmployeeServicePackEntity service_pack_employee_id,
			ValidityPeriodEntity validity_period_id, java.sql.Date start_date, java.sql.Date deactivation_date, float costpackage,
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

	@Temporal(TemporalType.DATE)
	@Column(name = "Start_date")
	private Date start_date;

	@Temporal(TemporalType.DATE)
	@Column(name = "Deactivation_date")
	private Date deactivation_date;

	@Column(name = "Costpackage", unique = true, nullable = false)
	private float costpackage;

	@Column(name = "Totalcostoptionalproducts", unique = true, nullable = false)
	private float totalcostoptionalproducts;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Validity_period_id") // owner della relazione associate
	private ValidityPeriodEntity validity_period_id;

	@ManyToMany(fetch=FetchType.EAGER )
	@JoinTable(name = "has", joinColumns = {@JoinColumn(name = "Service_pack_id")}, inverseJoinColumns = {@JoinColumn(name = "Optional_product_id")})
	private List<OptionalProductEntity> optionalProductEntities; // owner of the relation has

	@OneToOne(mappedBy = "service_pack_id", cascade = CascadeType.ALL, orphanRemoval = true)
	private OrderEntity orders; // relazione in

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Service_pack_employee_id")
	private EmployeeServicePackEntity service_pack_employee_id; // owner della relazione made_of

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getDeactivation_date() {
		return deactivation_date;
	}

	public void setDeactivation_date(Date deactivation_date) {
		this.deactivation_date = deactivation_date;
	}

	public float getCostpackage() {
		return costpackage;
	}

	public void setCostpackage(float costpackage) {
		this.costpackage = costpackage;
	}

	public float getTotalcostoptionalproducts() {
		return totalcostoptionalproducts;
	}

	public void setTotalcostoptionalproducts(float totalcostoptionalproducts) {
		this.totalcostoptionalproducts = totalcostoptionalproducts;
	}

	public ValidityPeriodEntity getValidity_period_id() {
		return validity_period_id;
	}

	public void setValidity_period_id(ValidityPeriodEntity validity_period_id) {
		this.validity_period_id = validity_period_id;
	}

	public List<OptionalProductEntity> getOptionalProductEntities() {
		return optionalProductEntities;
	}

	public void setOptionalProductEntities(List<OptionalProductEntity> optionalProductEntities) {
		this.optionalProductEntities = optionalProductEntities;
	}

	public OrderEntity getOrders() {
		return orders;
	}

	public void setOrders(OrderEntity orders) {
		this.orders = orders;
	}

	public EmployeeServicePackEntity getService_pack_employee_id() {
		return service_pack_employee_id;
	}

	public void setService_pack_employee_id(EmployeeServicePackEntity service_pack_employee_id) {
		this.service_pack_employee_id = service_pack_employee_id;
	}

}
