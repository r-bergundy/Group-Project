package com.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonManagedReference;


/**
 * The persistent class for the accesscapability database table.
 * 
 */
@Entity
@Table(name="accesscapability")
@NamedQuery(name="AccessCapability.findAll", query="SELECT a FROM AccessCapability a")
public class AccessCapability implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="AccessCapabilityID")
	private int accessCapabilityID;

	@Column(name="AccessName")
	private String accessName;

	@OneToMany(mappedBy="accesscapability", cascade = CascadeType.ALL)
	private List<UEAccessCapability> ueaccesscapabilities;

	public AccessCapability() {
	}

	public int getAccessCapabilityID() {
		return this.accessCapabilityID;
	}

	public void setAccessCapabilityID(int accessCapabilityID) {
		this.accessCapabilityID = accessCapabilityID;
	}

	public String getAccessName() {
		return this.accessName;
	}

	public void setAccessName(String accessName) {
		this.accessName = accessName;
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
		ueaccesscapability.setAccesscapability(this);

		return ueaccesscapability;
	}

	public UEAccessCapability removeUeaccesscapability(UEAccessCapability ueaccesscapability) {
		getUeaccesscapabilities().remove(ueaccesscapability);
		ueaccesscapability.setAccesscapability(null);

		return ueaccesscapability;
	}

}