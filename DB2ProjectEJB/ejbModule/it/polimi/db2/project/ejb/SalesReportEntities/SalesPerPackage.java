package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;


/**
 * The persistent class for the salesPerPackage database table.
 * 
 */
@Entity
@NamedQuery(name="SalesPerPackage.findAll", query="SELECT s FROM SalesPerPackage s")  // TODO ?non serve trovare per service pack?
@Table(name = "salesPerPackage")
public class SalesPerPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EmployeeServicePack_id", nullable = false)
	private int employeeServicePack_id;
	
	@OneToOne
    @JoinColumn(name = "EmployeeServicePack_id", updatable=false, insertable=false)
    private EmployeeServicePackEntity empServicePack;

	@Column(name = "totalSalesWithOptionalProduct", nullable = false)
	private int totalSalesWithOptionalProduct;

	@Column(name = "totalSalesWithoutOptionalProduct", nullable = false)
	private int totalSalesWithoutOptionalProduct;
	
	//TODO ?Manca il costruttore?

	public SalesPerPackage() {
	}

	public int getEmployeeServicePack_id() {
		return this.employeeServicePack_id;
	}

	public EmployeeServicePackEntity getEmpServicePack() {
		return empServicePack;
	}

	public void setEmpServicePack(EmployeeServicePackEntity empServicePack) {
		this.empServicePack = empServicePack;
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