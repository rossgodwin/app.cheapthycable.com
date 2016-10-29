package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.UserDAO;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.ILongId;

public class UserDAOImpl extends GenericHibernateDAO<User, ILongId> implements UserDAO {

	@Override
	public User findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}
	
	@Override
	public User findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.id = :id");
		q.setParameter("id", id);
		User r = (User) q.uniqueResult();
		return r;
	}

	@Override
	public User findByUsername(String username) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.username = :username");
		q.setParameter("username", username);
		User r = (User) q.uniqueResult();
		return r;
	}
	
	@Override
	public User findByEmail(String email) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.email = :email");
		q.setParameter("email", email);
		User r = (User) q.uniqueResult();
		return r;
	}

	@Override
	public User findByAccountId(long accountId) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.account.id = :accountId");
		q.setParameter("accountId", accountId);
		User r = (User) q.uniqueResult();
		return r;
	}
}
