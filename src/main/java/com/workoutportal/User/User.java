package com.workoutportal.User;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workoutportal.WorkOut.WorkOut;
import org.springframework.cache.annotation.CacheConfig;

@Entity
@Table(name = "User")
@CacheConfig(cacheNames = "user")
public class User {
	
	private interface Table {
		String USERID = "USERID";
		String USERNAME = "USERNAME";
		String PASSWORD = "PASSWORD";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = Table.USERID)
	private Long userId;

	@Column(name = Table.PASSWORD)
	private String password;

	@Column(name = Table.USERNAME, unique = true)
	private String userName;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public User(Long userId, String password, String userName, List<WorkOut> workout) {
		super();
		this.userId = userId;
		this.password = password;
		this.userName = userName;
	}
	
	public User(Long userId, String password, String userName) {
		super();
		this.userId = userId;
		this.password = password;
		this.userName = userName;
	}

	public User() {
		super();
	}
}
