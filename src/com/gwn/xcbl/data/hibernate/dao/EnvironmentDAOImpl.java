package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.HibernateSessionFactory;
import com.gwn.xcbl.data.hibernate.entity.Environment;

public class EnvironmentDAOImpl {

	public Environment getEnvironment() {
		Query q = HibernateSessionFactory.getSession().createQuery("from " + Environment.class.getSimpleName());
		Environment r = (Environment) q.uniqueResult();
		return r;
	}
}
