package com.gwn.xcbl.bl.ba.email;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.gwn.xcbl.bl.mail.Emailer;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

public class BaEmailRun implements Runnable {

	private ServletContext servletCtx;
	
	public BaEmailRun(ServletContext servletCtx) {
		this.servletCtx = servletCtx;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		HibernateSessionFactory.getSession();
		
//		HibernateSessionFactory.getSession();
		HibernateUtil.getSessionFactory();
		HibernateUtil.beginTransaction();
		
		Account account = DAOFactory.getInstance().getAccountDAO().findById(1351L, true);
		List<Bill> bills = new ArrayList<Bill>();
		bills.add(DAOFactory.getInstance().getBillDAO().findById(15005L, true));
		bills.add(DAOFactory.getInstance().getBillDAO().findById(15006L, true));
		
		try {
			Email email = new BaEmailBuilder(servletCtx).buildEmail(account, bills);
			Emailer.sendEmail(email);
		} catch (URISyntaxException e) {
		}
	}
}
