package com.gwn.xcbl.web.servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.gwn.xcbl.bl.ba.email.BaEmailRun;
import com.gwn.xcbl.bl.bill.dsq.DsqBillSyncRun;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.EnvironmentDAOImpl;
import com.gwn.xcbl.data.model.AppData;

@WebListener
public class AppSrvltCtxLstnr implements ServletContextListener {

	/**
	 * http://stackoverflow.com/questions/4691132/how-to-run-a-background-task-in-a-servlet-based-web-application
	 */
	private ScheduledExecutorService scheduler;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.beginTransaction();
		
		AppData.getInstance().load(new EnvironmentDAOImpl().getEnvironment());
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		if (AppData.getInstance().isEnvProd()) {
			scheduler.scheduleAtFixedRate(new BaEmailRun(arg0.getServletContext()), 0, 4, TimeUnit.HOURS);
		}
		scheduler.scheduleAtFixedRate(new DsqBillSyncRun(), 0, 3, TimeUnit.HOURS);
		
		HibernateUtil.closeSession();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (scheduler != null) {
			scheduler.shutdownNow();
		}
	}
}
