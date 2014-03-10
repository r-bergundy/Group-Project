package com.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the failureclass database table.
 * 
 */
@Entity
@Table(name="failureclass")

@NamedQuery(name="FailureClass.findAll", query="SELECT f FROM FailureClass f")



public class FailureClass implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FailureClassID")
	private int failureClassID;

	@Column(name="Description")
	private String description;

	@OneToMany(mappedBy="failureclass")
	private List<CallFailure> callfailures;

	public FailureClass() {
	}

	public int getFailureClassID() {
		return this.failureClassID;
	}

	public void setFailureClassID(int failureClassID) {
		this.failureClassID = failureClassID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonIgnore
	public List<CallFailure> getCallfailures() {
		return this.callfailures;
	}

	public void setCallfailures(List<CallFailure> callfailures) {
		this.callfailures = callfailures;
	}

	public CallFailure addCallfailure(CallFailure callfailure) {
		getCallfailures().add(callfailure);
		callfailure.setFailureclass(this);

		return callfailure;
	}

	public CallFailure removeCallfailure(CallFailure callfailure) {
		getCallfailures().remove(callfailure);
		callfailure.setFailureclass(null);

		return callfailure;
	}
	
	public Object getPrimaryKey(){
		return failureClassID;
	};

}