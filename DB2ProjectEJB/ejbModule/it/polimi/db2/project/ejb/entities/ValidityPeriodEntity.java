package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "validity_period")
@NamedQueries({
    @NamedQuery(name = "ValidityPeriodEntity.getValidityPeriods", query = "SELECT x FROM ValidityPeriodEntity x")
})
public class ValidityPeriodEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
	
	@Column(name = "Monthly_fee", nullable = false)
    private int monthly_fee;

    @Column(name = "Months", nullable = false)
    private int months;
    
    @OneToMany(mappedBy = "validity_period_id") // relation associate
    private List<ServicePackageEntity> servicePackageEntities;
    
    @ManyToMany(mappedBy = "validityPeriodEntity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE}) // relation offers
    private List<EmployeeServicePackEntity> employeeServicePackEntity = new ArrayList<>();
    
    //fetch = FetchType.EAGER, cascade = CascadeType.REMOVE

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMonthly_fee() {
		return monthly_fee;
	}

	public void setMonthly_fee(int monthly_fee) {
		this.monthly_fee = monthly_fee;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}
    

}
