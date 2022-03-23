package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.AlertEntity;


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
	@Column(name = "Alert_id", nullable = false)
	private int alert_id;
	
	@OneToOne
    @JoinColumn(name = "Alert_id", updatable=false, insertable=false)
    private AlertEntity alert;

	public Alert() {
	}

	public int getAlert_id() {
		return this.alert_id;
	}

	public void setAlert_id(int alert_id) {
		this.alert_id = alert_id;
	}

	public AlertEntity getAlert() {
		return alert;
	}

	public void setAlert(AlertEntity alert) {
		this.alert = alert;
	}

}