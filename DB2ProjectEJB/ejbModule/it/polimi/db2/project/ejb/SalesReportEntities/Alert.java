package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the alerts database table.
 * 
 */
@Entity
@Table(name="alerts")
@NamedQuery(name="Alert.findAll", query="SELECT a FROM Alert a")
public class Alert implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int alert_id;

	public Alert() {
	}

	public int getAlert_id() {
		return this.alert_id;
	}

	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}

}