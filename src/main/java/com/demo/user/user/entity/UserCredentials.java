package com.demo.user.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name= "user")
public class UserCredentials {
	
	@Id
	@Size(min=8)
	@Column(name = "username",unique = true)
	private String username;
	
	@Size(min=8)
	@Column(name = "password")
	private String password;

	protected UserCredentials() {
		super();
	}
	
	public UserCredentials(@Size(min = 8) String username, @Size(min = 8) String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserCredentials [username=" + username + ", password=" + password + "]";
	}

}
