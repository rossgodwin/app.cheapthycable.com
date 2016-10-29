package com.gwn.xcbl.data.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	private static SessionFactory sessionFactory;

	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}

	public static void rebuildSessionFactory() {
		try {
			rebuildSessionFactory(new Configuration().configure("com/gwn/xcbl/data/hibernate/hibernate.cfg.xml"));
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	public static void rebuildSessionFactory(Configuration config) {
		try {
			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
