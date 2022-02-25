package it.polimi.db2.project.ejb.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
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
	    
	 // ****************** TO BE FIXED

	    @OneToMany
	    private int user_id;
}
