package com.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The persistent class for the callfailure database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name = "callfailure")
@NamedQueries({
		@NamedQuery(name = "CallFailure.findAll", query = "SELECT c FROM CallFailure c"),
		@NamedQuery(name = "Find EventID/Cause Code for IMSI", query = "SELECT cf, ec, d FROM "
				+ "CallFailure cf, EventCause ec, Device d WHERE cf.eventcause = ec and cf.device = d"
				+ " and d.imsi = :paramIMSI"),
		@NamedQuery(name = "Find unique Cause Codes for IMSI", query = "SELECT cf, ec, d FROM "
				+ "CallFailure cf, EventCause ec, Device d WHERE cf.eventcause = ec and cf.device = d"
				+ " and d.imsi = :paramIMSI GROUP BY ec.causeCode"),
		@NamedQuery(name = "Return IMSIs with Failure in Time", query = "SELECT distinct cf.device from CallFailure cf"
				+ " WHERE cf.dateTime >= :startTime AND cf.dateTime <= :endTime"),
		@NamedQuery(name = "Find Count Failures For IMSI in Time", query = "SELECT cf.device from CallFailure cf"
				+ " WHERE cf.device =  :paramIMSI"
				+ " AND cf.dateTime >= :startTime AND cf.dateTime <= :endTime")
})

public class CallFailure implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CallFailureID")
	private int callFailureID;

	@Column(name = "CellID")
	private int cellID;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateTime")
	private Date dateTime;

	@Column(name = "Duration")
	private int duration;

	@Column(name = "HIER321ID")
	private String hier321id;

	@Column(name = "HIER32ID")
	private String hier32id;

	@Column(name = "HIER3ID")
	private String hier3id;

	private String NEVersion;

	@ManyToOne
	@JoinColumn(name = "FailureClassID")
	private FailureClass failureclass;

	@ManyToOne
	@JoinColumn(name = "IMSI")
	private Device device;

	@ManyToOne
	@JoinColumn(name = "EventCauseID")
	private EventCause eventcause;

	public CallFailure() {
	}

	public int getCallFailureID() {
		return this.callFailureID;
	}

	public void setCallFailureID(int callFailureID) {
		this.callFailureID = callFailureID;
	}

	public int getCellID() {
		return this.cellID;
	}

	public void setCellID(int cellID) {
		this.cellID = cellID;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getHier321id() {
		return this.hier321id;
	}

	public void setHier321id(String hier321id) {
		this.hier321id = hier321id;
	}

	public String getHier32id() {
		return this.hier32id;
	}

	public void setHier32id(String hier32id) {
		this.hier32id = hier32id;
	}

	public String getHier3id() {
		return this.hier3id;
	}

	public void setHier3id(String hier3id) {
		this.hier3id = hier3id;
	}

	public String getNEVersion() {
		return this.NEVersion;
	}

	public void setNEVersion(String NEVersion) {
		this.NEVersion = NEVersion;
	}

	@JsonBackReference
	public FailureClass getFailureclass() {
		return this.failureclass;
	}

	public void setFailureclass(FailureClass failureclass) {
		this.failureclass = failureclass;
	}

	@JsonBackReference
	public Device getDevice() {
		return this.device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@JsonBackReference
	public EventCause getEventcause() {
		return this.eventcause;
	}

	public void setEventcause(EventCause eventcause) {
		this.eventcause = eventcause;
	}
	
	public Object getPrimaryKey(){
		return callFailureID;
	};

}