package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the insolvent database table.
 * 
 */
@Entity
@NamedQuery(name="Insolvent.findAll", query="SELECT i FROM Insolvent i")
public class Insolvent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int user_id;

	public Insolvent() {
	}

	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}