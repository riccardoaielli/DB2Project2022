package it.polimi.db2.project.ejb.SalesReportEntities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the best_seller_OP database table.
 * 
 */
@Entity
@Table(name = "best_seller_OP", schema = "db2Project")
@NamedQuery(name="Best_seller_OP.findAll", query="SELECT b FROM Best_seller_OP b")
public class Best_seller_OP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int optional_product_id;

	private float totalSales;

	public Best_seller_OP() {
	}

	public int getOptional_product_id() {
		return this.optional_product_id;
	}

	public void setOptional_product_id(int optional_product_id) {
		this.optional_product_id = optional_product_id;
	}

	public float getTotalSales() {
		return this.totalSales;
	}

	public void setTotalSales(float totalSales) {
		this.totalSales = totalSales;
	}

}