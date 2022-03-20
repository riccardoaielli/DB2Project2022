package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the salesPerPackage database table.
 * 
 */
@Entity
@NamedQuery(name="SalesPerPackage.findAll", query="SELECT s FROM SalesPerPackage s")
public class SalesPerPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int employeeServicePack_id;

	private int totalSalesWithOptionalProduct;

	private int totalSalesWithoutOptionalProduct;

	public SalesPerPackage() {
	}

	public int getEmployeeServicePack_id() {
		return this.employeeServicePack_id;
	}

	public void setEmployeeServicePack_id(int employeeServicePack_id) {
		this.employeeServicePack_id = employeeServicePack_id;
	}

	public int getTotalSalesWithOptionalProduct() {
		return this.totalSalesWithOptionalProduct;
	}

	public void setTotalSalesWithOptionalProduct(int totalSalesWithOptionalProduct) {
		this.totalSalesWithOptionalProduct = totalSalesWithOptionalProduct;
	}

	public int getTotalSalesWithoutOptionalProduct() {
		return this.totalSalesWithoutOptionalProduct;
	}

	public void setTotalSalesWithoutOptionalProduct(int totalSalesWithoutOptionalProduct) {
		this.totalSalesWithoutOptionalProduct = totalSalesWithoutOptionalProduct;
	}

}