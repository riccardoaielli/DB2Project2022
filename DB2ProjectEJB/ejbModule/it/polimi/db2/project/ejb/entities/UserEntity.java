package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "UserEntity.checkCredentials", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password"),
        @NamedQuery(name = "UserEntity.findByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username"),
        @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Username", nullable = false, length = 45)
    private String username;

    @Column(name = "Password", nullable = false, length = 45)
    private String password;

    @Column(name = "Email", nullable = false, length = 90)
    private String email;
    
    @Column(name = "Flag_ins", nullable = false)
    private boolean flag_ins;
    
    @Column(name = "NumberOfFailedPayments", nullable = false)
    private int numberOfFailedPayments;


    @OneToMany(mappedBy = "user_id", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user_alert", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private AlertEntity alert;
    
    
    public UserEntity(String username, String password, String email, boolean flag) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.flag_ins = flag;

    }
    
    public int getNumberOfFailedPayments() {
		return numberOfFailedPayments;
	}

	public void setNumberOfFailedPayments(int numberOfFailedPayments) {
		this.numberOfFailedPayments = numberOfFailedPayments;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	public AlertEntity getAlert() {
		return alert;
	}

	public void setAlert(AlertEntity alert) {
		this.alert = alert;
	}

	public void increaseNumberOfFailedPayments() {
    	numberOfFailedPayments++;
    }

    public UserEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean getFlag_ins() {
        return this.flag_ins;
    }

    public void setFlag_ins(boolean flag_ins) {
        this.flag_ins = flag_ins;
    }

}
