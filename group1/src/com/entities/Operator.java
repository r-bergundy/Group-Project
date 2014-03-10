package com.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the operator database table.
 * 
 */
@Entity
@Table(name="operator")
@NamedQuery(name="Operator.findAll", query="SELECT o FROM Operator o")
public class Operator implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OperatorID")
	private int operatorID;

	@Column(name="Country")
	private String country;

	@Column(name="MCC")
	private int mcc;

	@Column(name="MNC")
	private int mnc;

	@Column(name="OperatorName")
	private String operatorName;

	@OneToMany(mappedBy="operator")
	private List<Device> devices;

	public Operator() {
	}

	public int getOperatorID() {
		return this.operatorID;
	}

	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getMcc() {
		return this.mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return this.mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@JsonManagedReference
	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device addDevice(Device device) {
		getDevices().add(device);
		device.setOperator(this);

		return device;
	}

	public Device removeDevice(Device device) {
		getDevices().remove(device);
		device.setOperator(null);

		return device;
	}
	
	public Object getPrimaryKey(){
		return operatorID;
	};

}