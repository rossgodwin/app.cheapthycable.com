package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Session;

import com.gwn.xcbl.data.hibernate.HibernateUtil;

public class BaseDAO {

	protected Session getSession() {
//		return HibernateSessionFactory.getSession();
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
