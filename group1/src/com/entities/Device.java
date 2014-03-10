package com.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;


/**
 * The persistent class for the device database table.
 * 
 */
@Entity
@Table(name="device")
@NamedQuery(name="Device.findAll", query="SELECT d FROM Device d")
public class Device implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IMSI")
	private String imsi;

	@OneToMany(mappedBy="device")
	private List<CallFailure> callfailures;

	@ManyToOne
	@JoinColumn(name="TAC")
	private UserEquipment userequipment;

	@ManyToOne
	@JoinColumn(name="OperatorID")
	private Operator operator;

	public Device() {
	}

	public String getImsi() {
		return this.imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	@JsonManagedReference
	public List<CallFailure> getCallfailures() {
		return this.callfailures;
	}

	public void setCallfailures(List<CallFailure> callfailures) {
		this.callfailures = callfailures;
	}

	public CallFailure addCallfailure(CallFailure callfailure) {
		getCallfailures().add(callfailure);
		callfailure.setDevice(this);

		return callfailure;
	}

	public CallFailure removeCallfailure(CallFailure callfailure) {
		getCallfailures().remove(callfailure);
		callfailure.setDevice(null);

		return callfailure;
	}

	@JsonBackReference
	public UserEquipment getUserequipment() {
		return this.userequipment;
	}

	public void setUserequipment(UserEquipment userequipment) {
		this.userequipment = userequipment;
	}

	@JsonBackReference
	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Object getPrimaryKey(){
		return imsi;
	};

}