package it.polimi.db2.project.ejb.entities;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "order")
@NamedQueries({
        
})

   
public class OrderEntity {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id", nullable = false)
	    private int id;

	    @Column(name = "Totalcost", nullable = false, length = 45)
	    private float totalcost;

	    @Column(name = "Isvalid", nullable = false, length = 45)
	    private boolean isvalid;
	    
	    @Column(name = "Timestamp", nullable = false, length = 45)
	    private Timestamp timestamp;
	 
	    @ManyToOne
	    @JoinColumn(name = "User_id", nullable = false)  
	    private UserEntity user_id;
	    
	    @OneToOne
	    @JoinColumn(name = "Service_pack_id", nullable = false)  
	    private ServicePackageEntity service_pack_id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Timestamp getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Timestamp timestamp) {
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
