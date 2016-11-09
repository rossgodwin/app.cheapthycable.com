package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.entity.Environment;

public class EnvironmentDAOImpl extends BaseDAO {

	public Environment getEnvironment() {
		Query q = getSession().createQuery("from " + Environment.class.getSimpleName());
		Environment r = (Environment) q.uniqueResult();
		return r;
	}
}
