package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;
import it.polimi.db2.project.ejb.entities.ValidityPeriodEntity;


/**
 * The persistent class for the numberTotalPurchasesPerESPAndValidityPeriod database table.
 * 
 */
@Entity
@NamedQuery(name="NumberTotalPurchasesPerESPAndValidityPeriod.findAll", query="SELECT n FROM NumberTotalPurchasesPerESPAndValidityPeriod n") // TODO ?non serve trovare per service pack e val period?
@Table(name = "numberTotalPurchasesPerESPAndValidityPeriod")
public class NumberTotalPurchasesPerESPAndValidityPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	@Column(name = "EmployeeServicePack_id", nullable = false)
	private int employeeServicePack_id;
	
	@OneToOne
    @JoinColumn(name = "EmployeeServicePack_id", updatable=false, insertable=false)
    private EmployeeServicePackEntity employeeServicePack;
	
	@Column(name = "TotalPurchases", nullable = false)
	private int totalPurchases;
	
	@Column(name = "Validity_period_id", nullable = false)
	private int validity_period_id;
	
	@OneToOne
    @JoinColumn(name = "Validity_period_id", updatable=false, insertable=false)
    private ValidityPeriodEntity validityPeriod;

	public NumberTotalPurchasesPerESPAndValidityPeriod() {
	}
	
	//TODO ?Manca il costruttore?

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeServicePack_id() {
		return this.employeeServicePack_id;
	}

	public void setEmployeeServicePack_id(int employeeServicePack_id) {
		this.employeeServicePack_id = employeeServicePack_id;
	}

	public int getTotalPurchases() {
		return this.totalPurchases;
	}

	public void setTotalPurchases(int totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public int getValidity_period_id() {
		return this.validity_period_id;
	}

	public void setValidity_period_id(int validity_period_id) {
		this.validity_period_id = validity_period_id;
	}

	public EmployeeServicePackEntity getEmployeeServicePack() {
		return employeeServicePack;
	}

	public void setEmployeeServicePack(EmployeeServicePackEntity employeeServicePack) {
		this.employeeServicePack = employeeServicePack;
	}

	public ValidityPeriodEntity getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriodEntity validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

}