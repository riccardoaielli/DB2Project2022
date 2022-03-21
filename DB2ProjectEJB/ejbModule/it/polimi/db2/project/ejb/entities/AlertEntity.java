package it.polimi.db2.project.ejb.entities;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alert", schema = "db2Project")
@NamedQueries({
        @NamedQuery(name = "AlertEntity.findUser", query = "SELECT u FROM UserEntity u WHERE u.id = :user_alert"),         
})
public class AlertEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
 
 	@Column(name = "Amount", nullable = false)
    private float amount;

    @Column(name = "Timestamp", nullable = false) // put by default from the db when the entry is created in the table
    private Timestamp timestamp;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_alert_id", nullable = false)  // alert has the fk of the user who created the alert
    private UserEntity user_alert;

    
	    public AlertEntity(float amount, Timestamp timestamp, UserEntity user_alert) {
	    	this.amount = amount;
	        this.timestamp = timestamp;
	        this.user_alert = user_alert;	      

	    }
    
    public AlertEntity(){
    	
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

//		public int getUser_alert() {
//			return user_alert;
//		}
//
//		public void setUser_alert(int user_alert) {
//			this.user_alert = user_alert;
//		}
	    

	    
}
