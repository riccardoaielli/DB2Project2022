package it.polimi.db2.project.ejb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alert")
@NamedQueries({
        @NamedQuery(name = "AlertEntity.findUser", query = "SELECT u FROM UserEntity u WHERE u.id = :user_alert"),
        
})
public class AlertEntity {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;
	 
	 	@Column(name = "Amount", nullable = false, length = 45)
	    private String amount;

	    @Column(name = "Timestamp", nullable = false, length = 45) // put by default from the db when the entry is created in the table
	    private String timestamp;
	    
	    @OneToOne
	    @JoinColumn(name = "User_alert", nullable = false)  // unidirectional, alert has the fk of the user who created the alert
	    private UserEntity user_alert;

	    
//	    public AlertEntity(String amount, String timestamp, UserEntity user_alert) {
//	    	this.amount = amount;
//	        this.timestamp = timestamp;
//	        this.user_alert = user_alert;
//	      
//
//	    }
	    public AlertEntity(){}
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
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
