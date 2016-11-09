package com.gwn.xcbl.data.shared;

import java.io.Serializable;

public class UserDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1456429100371276045L;

	private Long id = -1L;
	
	private AccountDTO account;
	
	private String username;
	
	private UserRole role;
	
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
