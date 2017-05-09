package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.slipp.UnAuthorizedException;
import support.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {
	public static final GuestUser GUEST_USER = new GuestUser();
	
	@Column(unique = true, nullable = false, length = 20)
	private String userId;
	
	@Column(nullable = false, length = 20)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(length = 50)
	private String email;
	
	public User() {
	}
	
	public User(String userId, String password, String name, String email) {
		this(0L, userId, password, name, email);
	}
	
	public User(long id, String userId, String password, String name, String email) {
		super(id);
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
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
	
	private boolean matchUserId(String userId) {
		return this.userId.equals(userId);
	}
	
	public void update(User loginUser, User target) {
		if (!matchUserId(loginUser.getUserId())) {
			throw new UnAuthorizedException();
		}
		
		if (!matchPassword(target.getPassword())) {
			return;
		}
		
		this.name = target.name;
		this.email = target.email;
	}
	
	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
	
	@JsonIgnore
	public boolean isGuestUser() {
		return false;
	}
	
    private static class GuestUser extends User {
		@Override
		public boolean isGuestUser() {
			return true;
		}
	}
	
	@Override
	public String toString() {
		return "User [id=" + getId() + ", userId=" + userId + ", name=" + name + ", email=" + email + "]";
	}
}
