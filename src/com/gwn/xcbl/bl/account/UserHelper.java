package com.gwn.xcbl.bl.account;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.UserRole;

public class UserHelper {

	public static User createUser(Account account, String username, String plainPassword, String email) {
		String ePwd = UserPasswordHelper.encryptPassword(plainPassword);
		
		User user = new User();
		user.setAccount(account);
		user.setUsername(username);
		user.setPassword(ePwd);
		user.setRole(UserRole.USER);
		user.setEmail(email);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(user);
		
		return user;
	}
	
	public static void updatePassword(User user, String plainPassword) {
		String ePwd = UserPasswordHelper.encryptPassword(plainPassword);
		user.setPassword(ePwd);
		HibernateUtil.getSessionFactory().getCurrentSession().update(user);
	}
}
