package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;

import it.polimi.db2.project.ejb.entities.OrderEntity;


@Entity
@Table(name="rejectedOrders")
@NamedQuery(name="RejectedOrder.findAll", query="SELECT r FROM RejectedOrder r")
public class RejectedOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Order_id", nullable = false)
	private int order_id;
	
	@OneToOne
    @JoinColumn(name = "Order_id", updatable=false, insertable=false)
    private OrderEntity order;

	public RejectedOrder() {
	}

	public int getOrder_id() {
		return this.order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

}