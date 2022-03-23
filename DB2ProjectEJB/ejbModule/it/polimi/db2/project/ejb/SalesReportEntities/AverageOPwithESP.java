package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.EmployeeServicePackEntity;



/**
 * The persistent class for the averageOPwithESP database table.
 * 
 */
@Entity
@NamedQuery(name="AverageOPwithESP.findAll", query="SELECT a FROM AverageOPwithESP a") // TODO ?non serve trovare per service pack?
@Table(name = "averageOPwithESP", schema = "db2Project")
public class AverageOPwithESP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EmployeeServicePack_id", nullable = false)
	private int employeeServicePack_id;
	
	@OneToOne
    @JoinColumn(name = "EmployeeServicePack_id", updatable=false, insertable=false)
    private EmployeeServicePackEntity employeeServicePack;

	@Column(name = "averageOPs", nullable = false)
	private float averageOPs;

	@Column(name = "totalOPsPerESP", nullable = false)
	private int totalOPsPerESP;

	@Column(name = "totalOrdersPerESP", nullable = false)
	private int totalOrdersPerESP;

	public AverageOPwithESP() {
	}
	
	//TODO ?Manca il costruttore?

	public int getEmployeeServicePack_id() {
		return this.employeeServicePack_id;
	}

	public void setEmployeeServicePack_id(int employeeServicePack_id) {
		this.employeeServicePack_id = employeeServicePack_id;
	}

	public float getAverageOPs() {
		return this.averageOPs;
	}

	public void setAverageOPs(float averageOPs) {
		this.averageOPs = averageOPs;
	}

	public int getTotalOPsPerESP() {
		return this.totalOPsPerESP;
	}

	public void setTotalOPsPerESP(int totalOPsPerESP) {
		this.totalOPsPerESP = totalOPsPerESP;
	}

	public int getTotalOrdersPerESP() {
		return this.totalOrdersPerESP;
	}

	public void setTotalOrdersPerESP(int totalOrdersPerESP) {
		this.totalOrdersPerESP = totalOrdersPerESP;
	}

	public EmployeeServicePackEntity getEmployeeServicePack() {
		return employeeServicePack;
	}

	public void setEmployeeServicePack(EmployeeServicePackEntity employeeServicePack) {
		this.employeeServicePack = employeeServicePack;
	}

}