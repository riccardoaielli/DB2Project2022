package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the numberTotalPurchasesPerESP database table.
 * 
 */
@Entity
@NamedQuery(name="NumberTotalPurchasesPerESP.findAll", query="SELECT n FROM NumberTotalPurchasesPerESP n")
public class NumberTotalPurchasesPerESP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int employeeServicePack_id;

	private int numbertotalpurchases;

	public NumberTotalPurchasesPerESP() {
	}

	public int getEmployeeServicePack_id() {
		return this.employeeServicePack_id;
	}

	public void setEmployeeServicePack_id(int employeeServicePack_id) {
		this.employeeServicePack_id = employeeServicePack_id;
	}

	public int getNumbertotalpurchases() {
		return this.numbertotalpurchases;
	}

	public void setNumbertotalpurchases(int numbertotalpurchases) {
		this.numbertotalpurchases = numbertotalpurchases;
	}

}