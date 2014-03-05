package com.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the eventcause database table.
 * 
 */
@Entity
@Table(name="eventcause")
@NamedQuery(name="EventCause.findAll", query="SELECT e FROM EventCause e")
public class EventCause implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EventCauseID")
	private int eventCauseID;

	@Column(name="CauseCode")
	private int causeCode;

	@Column(name="Description")
	private String description;

	@Column(name="EventID")
	private int eventID;

	@OneToMany(mappedBy="eventcause")
	private List<CallFailure> callfailures;

	public EventCause() {
	}

	public int getEventCauseID() {
		return this.eventCauseID;
	}

	public void setEventCauseID(int eventCauseID) {
		this.eventCauseID = eventCauseID;
	}

	public int getCauseCode() {
		return this.causeCode;
	}

	public void setCauseCode(int causeCode) {
		this.causeCode = causeCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEventID() {
		return this.eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
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
		callfailure.setEventcause(this);

		return callfailure;
	}

	public CallFailure removeCallfailure(CallFailure callfailure) {
		getCallfailures().remove(callfailure);
		callfailure.setEventcause(null);

		return callfailure;
	}

}