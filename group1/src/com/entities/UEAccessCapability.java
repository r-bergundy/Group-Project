package com.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * The persistent class for the ueaccesscapability database table.
 * 
 */
@Entity
@Table(name = "ueaccesscapability")
@NamedQueries({
		@NamedQuery(name = "UEAccesscCpability.findAll", query = "SELECT u FROM UEAccessCapability u"),
		@NamedQuery(name = "Find UE With UEType and AccessCapability", query = "SELECT ue, ueac, ac FROM "
				+ "UserEquipment ue, UEAccessCapability ueac, AccessCapability ac WHERE ue.UEType = :paramUEType and "
				+ "ue = ueac.userequipment and ueac.accesscapability = ac and ac.accessName = :paramAC")})
public class UEAccessCapability implements Serializable, IEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UEAccessCapabilityID;

	@ManyToOne
	@JoinColumn(name = "AccessCapabilityID")
	private AccessCapability accesscapability;

	@ManyToOne
	@JoinColumn(name = "TAC")
	private UserEquipment userequipment;

	public UEAccessCapability() {
	}

	public int getUEAccessCapabilityID() {
		return this.UEAccessCapabilityID;
	}

	public void setUEAccessCapabilityID(int UEAccessCapabilityID) {
		this.UEAccessCapabilityID = UEAccessCapabilityID;
	}

	@JsonBackReference
	public AccessCapability getAccesscapability() {
		return this.accesscapability;
	}

	public void setAccesscapability(AccessCapability accesscapability) {
		this.accesscapability = accesscapability;
	}

	@JsonBackReference
	public UserEquipment getUserequipment() {
		return this.userequipment;
	}

	public void setUserequipment(UserEquipment userequipment) {
		this.userequipment = userequipment;
	}
	
	public Object getPrimaryKey(){
		return UEAccessCapabilityID;
	};

}