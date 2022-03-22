package it.polimi.db2.project.ejb.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service", schema = "db2Project")
@NamedQueries({
    @NamedQuery(name = "ServiceEntity.findServiceByName", query = "SELECT s FROM ServiceEntity s WHERE s.type=:name"),
   
})
public class ServiceEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
 
 	@Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Min_fee")
    private float min_fee;
    
    @Column(name = "Sms_fee")
    private float sms_fee;
    
    @Column(name = "Min")
    private int min;
    
    @Column(name = "Sms")
    private int sms;
    
    @Column(name = "Gb_fee")
    private float gb_fee;
    
    @Column(name = "Gb")
    private int gb;
    
    @ManyToMany(mappedBy = "serviceEntities", fetch = FetchType.LAZY) // relation comprises
    private List<EmployeeServicePackEntity> employeeServicePackEntity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getMin_fee() {
		return min_fee;
	}

	public void setMin_fee(float min_fee) {
		this.min_fee = min_fee;
	}

	public float getSms_fee() {
		return sms_fee;
	}

	public void setSms_fee(float sms_fee) {
		this.sms_fee = sms_fee;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public float getGb_fee() {
		return gb_fee;
	}

	public void setGb_fee(float gb_fee) {
		this.gb_fee = gb_fee;
	}

	public int getGb() {
		return gb;
	}

	public void setGb(int gb) {
		this.gb = gb;
	}
	

}
