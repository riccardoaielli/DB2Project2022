package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the averageOPwithESP database table.
 * 
 */
@Entity
@NamedQuery(name="AverageOPwithESP.findAll", query="SELECT a FROM AverageOPwithESP a")
public class AverageOPwithESP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int employeeServicePack_id;

	private float averageOPs;

	private int totalOPsPerESP;

	private int totalOrdersPerESP;

	public AverageOPwithESP() {
	}

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

}