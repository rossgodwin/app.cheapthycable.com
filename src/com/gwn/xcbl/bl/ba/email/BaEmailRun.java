package com.gwn.xcbl.bl.ba.email;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;

import com.gwn.xcbl.bl.ba.BaAlertSentLogHelper;
import com.gwn.xcbl.bl.mail.Emailer;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.EnvironmentDAOImpl;
import com.gwn.xcbl.data.hibernate.entity.Environment;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.shared.ILongId;

public class BaEmailRun implements Runnable {

	private ServletContext servletCtx;
	
	public BaEmailRun(ServletContext servletCtx) {
		this.servletCtx = servletCtx;
	}
	
	@Override
	public void run() {
		HibernateUtil.beginTransaction();
		
		try {
			Environment environment = new EnvironmentDAOImpl().getEnvironment();
			if (environment.isBillAlertEnabled()) {
				emailAlerts();
			}
		} catch (Exception e) {
		} finally {
			HibernateUtil.closeSession();
		}
	}
	
	private void emailAlerts() {
		LocalDateTime currentDate = LocalDateTime.now();
		int ofst = 0;
		int lmt = 1;
		do {
			List<BaAlert> alerts = new BaEmailDAO().findAlertsToSend(currentDate, ofst, lmt);
			if (alerts.size() > 0) {
				Collection<Long> alertIds = ILongId.Utils.getIds(alerts);
				for (Long alertId : alertIds) {
					emailAlert(alertId);
				}
				ofst = ofst + lmt;
			} else {
				break;
			}
		} while (true);
	}
	
	private void emailAlert(long alertId) {
		BaAlert alert = DAOFactory.getInstance().getBaAlertDAO().findById(alertId, true);
		
		List<Bill> bills = new BaEmailDAO().findAlertBills(alert, 0, 5);
		if (bills.size() > 0) {
			try {
				Email email = new BaEmailBuilder(servletCtx).buildEmail(alert, bills);
				Emailer.sendEmail(email);
				BaAlertSentLogHelper.logSentAlert(alert);
				HibernateUtil.commit();
			} catch (URISyntaxException e) {
			}
		}
	}
}
