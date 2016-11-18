package com.localhost.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User implements Serializable {

	@EmbeddedId
	UserId id;

	@NotEmpty
	@Column(name = "user_name", nullable = false)
	private String userName;

	@NotEmpty
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "status")
	private String status;

	@Column(name = "email", nullable = false)
	private String email;

	@NotEmpty
	@Column(name = "registerTime", nullable = false)
	private String registerTime;

	@NotEmpty
	@Column(name = "lastActiveTime", nullable = false)
	private String lastActiveTime;

	public UserId getId() {
		return id;
	}

	public void setId(UserId id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(String lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

}
