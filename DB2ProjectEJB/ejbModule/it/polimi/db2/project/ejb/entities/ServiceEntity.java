package it.polimi.db2.project.ejb.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service")

public class ServiceEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;
 
 	@Column(name = "Type", nullable = false)
    private String type;

    @Column(name = "Min_fee", nullable = false)
    private int min_fee;
    
    @Column(name = "Sms_fee", nullable = false)
    private int sms_fee;
    
    @Column(name = "Min", nullable = false)
    private int min;
    
    @Column(name = "Sms", nullable = false)
    private int sms;
    
    @Column(name = "Gb_fee", nullable = false)
    private int gb_fee;
    
    @Column(name = "Gb", nullable = false)
    private int gb;
    
    @ManyToMany(mappedBy = "serviceEntities")
    private Collection<ServicePackageEntity> servicePackageEntities;

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

	public int getMin_fee() {
		return min_fee;
	}

	public void setMin_fee(int min_fee) {
		this.min_fee = min_fee;
	}

	public int getSms_fee() {
		return sms_fee;
	}

	public void setSms_fee(int sms_fee) {
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

	public int getGb_fee() {
		return gb_fee;
	}

	public void setGb_fee(int gb_fee) {
		this.gb_fee = gb_fee;
	}

	public int getGb() {
		return gb;
	}

	public void setGb(int gb) {
		this.gb = gb;
	}
	

}
