package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id @GeneratedValue
	private long id;
	
	@Column(unique = true, nullable = false, length = 20)
	private String userId;
	
	@Column(nullable = false, length = 20)
	private String password;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(length = 50)
	private String email;
	
	public User() {
	}
	
	public User(String userId, String password, String name, String email) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void update(User target) {
		this.password = target.password;
		this.name = target.name;
		this.email = target.email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", name=" + name + ", email=" + email + "]";
	}
}
