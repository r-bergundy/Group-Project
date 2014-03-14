package com.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@XmlRootElement
public class User implements Serializable, IEntity {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="UserID")
//	private int userID;

	@Column(name="Password")
	private String password;

	@Id
	@Column(name="UserName")
	private String userName;

	@Column(name="UserType")
	private UserType userType;

	public User() {
	}

//	public int getUserID() {
//		return this.userID;
//	}
//
//	public void setUserID(int userID) {
//		this.userID = userID;
//	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public Object getPrimaryKey(){
		return userName;
	};


}