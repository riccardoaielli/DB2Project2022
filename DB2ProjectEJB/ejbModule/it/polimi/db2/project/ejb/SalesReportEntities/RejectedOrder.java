package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rejectedOrders database table.
 * 
 */
@Entity
@Table(name="rejectedOrders")
@NamedQuery(name="RejectedOrder.findAll", query="SELECT r FROM RejectedOrder r")
public class RejectedOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int order_id;

	public RejectedOrder() {
	}

	public int getOrder_id() {
		return this.order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

}