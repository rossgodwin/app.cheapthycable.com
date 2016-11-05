package com.gwn.xcbl.common;

import java.security.Principal;

import com.gwn.xcbl.data.shared.UserRole;

public class UserPrincipal implements Principal {

	private long accountId;
	
	private long userId;
	
	private String username;
	
	private UserRole role;

	public UserPrincipal() {}
	
	public UserPrincipal(long accountId, long userId, String username, UserRole role) {
		this.accountId = accountId;
		this.userId = userId;
		this.username = username;
		this.role = role;
	}

	@Override
	public String getName() {
		return username;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
}
