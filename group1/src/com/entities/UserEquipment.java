package com.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the userequipment database table.
 * 
 */
@Entity
@Table(name="userequipment")
@NamedQueries({
	@NamedQuery(name="UserEquipment.findAll", query="SELECT u FROM UserEquipment u"),
	@NamedQuery(name = "Find UE With UEType", query = "SELECT ue FROM "
			+ "UserEquipment ue WHERE ue.UEType = :paramUEType"),
			@NamedQuery(name = "Find Count Failures For UE in Time", query = "SELECT cf, ue, d from CallFailure "
					+ "cf, UserEquipment ue, Device d WHERE cf.device = d AND d.userequipment = ue AND ue.tac = :tac "
					+ "AND cf.dateTime >= :startTime AND cf.dateTime <= :endTime")})

public class UserEquipment implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TAC")
	private int tac;

	@Column(name="InputMode")
	private String inputMode;

	@Column(name="Manufacturer")
	private String manufacturer;

	@Column(name="MarketingName")
	private String marketingName;

	@Column(name="Model")
	private String model;

	@Column(name="OperatingSystem")
	private String operatingSystem;

	private String UEType;

	@Column(name="VenderName")
	private String venderName;

	@OneToMany(mappedBy="userequipment")
	@JsonManagedReference
	private List<Device> devices;

	@OneToMany(mappedBy="userequipment")
	private List<UEAccessCapability> ueaccesscapabilities;

	public UserEquipment() {
	}

	public int getTac() {
		return this.tac;
	}

	public void setTac(int tac) {
		this.tac = tac;
	}

	public String getInputMode() {
		return this.inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMarketingName() {
		return this.marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOperatingSystem() {
		return this.operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getUEType() {
		return this.UEType;
	}

	public void setUEType(String UEType) {
		this.UEType = UEType;
	}

	public String getVenderName() {
		return this.venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device addDevice(Device device) {
		getDevices().add(device);
		device.setUserequipment(this);

		return device;
	}

	public Device removeDevice(Device device) {
		getDevices().remove(device);
		device.setUserequipment(null);

		return device;
	}

	@JsonManagedReference	
	public List<UEAccessCapability> getUeaccesscapabilities() {
		return this.ueaccesscapabilities;
	}

	public void setUeaccesscapabilities(List<UEAccessCapability> ueaccesscapabilities) {
		this.ueaccesscapabilities = ueaccesscapabilities;
	}

	public UEAccessCapability addUeaccesscapability(UEAccessCapability ueaccesscapability) {
		getUeaccesscapabilities().add(ueaccesscapability);
		ueaccesscapability.setUserequipment(this);

		return ueaccesscapability;
	}

	public UEAccessCapability removeUeaccesscapability(UEAccessCapability ueaccesscapability) {
		getUeaccesscapabilities().remove(ueaccesscapability);
		ueaccesscapability.setUserequipment(null);

		return ueaccesscapability;
	}
	
	public Object getPrimaryKey(){
		return tac;
	};


}