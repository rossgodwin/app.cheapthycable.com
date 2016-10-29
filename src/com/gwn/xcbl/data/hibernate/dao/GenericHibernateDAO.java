package com.gwn.xcbl.data.hibernate.dao;

import org.hibernate.Session;

import com.gwn.xcbl.data.dao.GenericDAO;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.shared.ILongId;

/**
 * https://developer.jboss.org/wiki/GenericDataAccessObjects
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericHibernateDAO<T, ID extends ILongId> implements GenericDAO<T, ID> {

	protected Session getSession() {
//		return HibernateSessionFactory.getSession();
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
