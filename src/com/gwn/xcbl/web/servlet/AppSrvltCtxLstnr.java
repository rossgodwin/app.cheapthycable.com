package com.gwn.xcbl.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.gwn.xcbl.data.hibernate.dao.EnvironmentDAOImpl;
import com.gwn.xcbl.data.model.AppData;

@WebListener
public class AppSrvltCtxLstnr implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		AppData.getInstance().setEnvironment(new EnvironmentDAOImpl().getEnvironment());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}
