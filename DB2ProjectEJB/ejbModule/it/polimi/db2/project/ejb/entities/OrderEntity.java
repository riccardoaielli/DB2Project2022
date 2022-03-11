package it.polimi.db2.project.ejb.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order")
@NamedQueries({
        
})

   
public class OrderEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;

	    @Column(name = "Cost", nullable = false, length = 45)
	    private String cost;

	    @Column(name = "Start_date", nullable = false, length = 45)
	    private String start_date;

	    @Column(name = "Status", nullable = false, length = 45)
	    private String status;
	    
	    @Column(name = "Timestamp", nullable = false, length = 45)
	    private String timestamp;
	 
	    @ManyToOne
	    @JoinColumn(name = "User_id", nullable = false)  
	    private UserEntity user_id;
	    
	    @ManyToOne
	    @JoinColumn(name = "Service_pack_id", nullable = false)  
	    private ServicePackageEntity service_pack_id; 

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCost() {
			return cost;
		}

		public void setCost(String cost) {
			this.cost = cost;
		}

		public String getStart_date() {
			return start_date;
		}

		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public UserEntity getUser_id() {
			return user_id;
		}

		public void setUser_id(UserEntity user_id) {
			this.user_id = user_id;
		}

		public ServicePackageEntity getService_pack_id() {
			return service_pack_id;
		}

		public void setService_pack_id(ServicePackageEntity service_pack_id) {
			this.service_pack_id = service_pack_id;
		}
	    
	    
	    
}
