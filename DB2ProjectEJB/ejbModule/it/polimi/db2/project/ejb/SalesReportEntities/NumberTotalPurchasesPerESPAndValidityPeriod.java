package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the numberTotalPurchasesPerESPAndValidityPeriod database table.
 * 
 */
@Entity
@NamedQuery(name="NumberTotalPurchasesPerESPAndValidityPeriod.findAll", query="SELECT n FROM NumberTotalPurchasesPerESPAndValidityPeriod n")
public class NumberTotalPurchasesPerESPAndValidityPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int employeeServicePack_id;

	private int totalPurchases;

	private int validity_period_id;

	public NumberTotalPurchasesPerESPAndValidityPeriod() {
	}

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

}