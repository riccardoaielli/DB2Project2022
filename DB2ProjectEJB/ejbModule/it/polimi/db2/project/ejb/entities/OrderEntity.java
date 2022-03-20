package it.polimi.db2.project.ejb.entities;

import java.sql.Date;
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

@Table(name = "order")
@NamedQueries({ @NamedQuery(name = "OrderEntity.findOrderById", query = "SELECT o FROM OrderEntity o WHERE o.id = :order_id"),

		@NamedQuery(name = "OrderEntity.findFailedOrdersByUserId", query = "SELECT o FROM OrderEntity o WHERE o.user_id = :user AND o.isvalid=false")

})

@Entity
public class OrderEntity {
	
	public OrderEntity() {
	}

	public OrderEntity(Timestamp timestamp, float totalcost, UserEntity user_id, ServicePackageEntity service_pack_id,
			boolean isvalid) {
		
		this.timestamp = timestamp;
		this.totalcost = totalcost;
		this.user_id = user_id;
		this.service_pack_id = service_pack_id;
		this.isvalid = isvalid;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	private int id;

	@Column(name = "Totalcost", nullable = false)
	private float totalcost;

	@Column(name = "Isvalid", nullable = false)
	private boolean isvalid;

	@Column(name = "Timestamp", nullable = false)
	private Timestamp timestamp;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "User_id", nullable = false)
	private UserEntity user_id;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "Service_pack_id", nullable = false)
	private ServicePackageEntity service_pack_id;

	public float getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(float totalcost) {
		this.totalcost = totalcost;
	}

	public boolean getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
	}

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
