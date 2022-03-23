package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.UserEntity;


/**
 * The persistent class for the insolvent database table.
 * 
 */
@Entity
@Table(name="insolvent")
@NamedQuery(name="Insolvent.findAll", query="SELECT i FROM Insolvent i")
public class Insolvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "User_id", nullable = false)
	private int user_id;
	
	@OneToOne
    @JoinColumn(name = "User_id")
    private UserEntity user;

	public Insolvent() {
	}

	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}