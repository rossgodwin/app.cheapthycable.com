package com.gwn.xcbl.data.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure("com/gwn/xcbl/data/hibernate/hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void beginTransaction() {
		getSessionFactory().getCurrentSession().beginTransaction();
	}

	public static void commit() {
		getSessionFactory().getCurrentSession().getTransaction().commit();
		beginTransaction();
	}

	public static void rollback() {
		getSessionFactory().getCurrentSession().getTransaction().rollback();
		getSessionFactory().getCurrentSession().beginTransaction();
	}
}
