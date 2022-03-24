package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;


@Entity
@NamedQuery(name="NumberTotalPurchasesPerESP.findAll", query="SELECT n FROM NumberTotalPurchasesPerESP n") 
@Table(name = "numberTotalPurchasesPerESP")
public class NumberTotalPurchasesPerESP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EmployeeServicePack_id", nullable = false)
	private int employeeServicePack_id;
	
	@OneToOne
    @JoinColumn(name = "EmployeeServicePack_id", updatable=false, insertable=false)
    private EmployeeServicePackEntity empoyeeServicePack;

	@Column(name = "Numbertotalpurchases", nullable = false)
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

	public EmployeeServicePackEntity getEmpoyeeServicePack() {
		return empoyeeServicePack;
	}

	public void setEmpoyeeServicePack(EmployeeServicePackEntity empoyeeServicePack) {
		this.empoyeeServicePack = empoyeeServicePack;
	}

}