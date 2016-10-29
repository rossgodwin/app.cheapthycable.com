package com.gwn.xcbl.data.dao;

import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.ILongId;

public interface UserDAO extends GenericDAO<User, ILongId> {

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public User findByAccountId(long accountId);
}
