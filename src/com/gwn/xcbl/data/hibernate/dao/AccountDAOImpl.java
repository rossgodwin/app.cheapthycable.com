package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.AccountDAO;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.shared.ILongId;

public class AccountDAOImpl extends GenericHibernateDAO<Account, ILongId> implements AccountDAO {

	@Override
	public Account findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public Account findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + Account.class.getSimpleName() + " u where u.id = :id");
		q.setParameter("id", id);
		Account r = (Account) q.uniqueResult();
		return r;
	}
}
