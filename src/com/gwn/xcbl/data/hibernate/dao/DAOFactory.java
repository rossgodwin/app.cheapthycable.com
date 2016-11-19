package com.gwn.xcbl.data.hibernate.dao;

import com.gwn.xcbl.data.dao.AccountDAO;
import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.dao.GeoZipCodeDAO;
import com.gwn.xcbl.data.dao.ProviderDAO;
import com.gwn.xcbl.data.dao.UserDAO;
import com.gwn.xcbl.data.dao.ba.BaAlertDAO;
import com.gwn.xcbl.data.dao.ba.BaAlertSentLogDAO;
import com.gwn.xcbl.data.hibernate.dao.ba.BaAlertDAOImpl;
import com.gwn.xcbl.data.hibernate.dao.ba.BaAlertSentLogDAOImpl;
import com.gwn.xcbl.data.hibernate.dao.be.BeDAOImpl;
import com.gwn.xcbl.data.hibernate.dao.bill.dsq.DsqBillCommentDAOImpl;

public class DAOFactory {

	private static DAOFactory instance;
	
	private AccountDAO accountDAO;
	
	private BillDAO billDAO;
	
	private GeoZipCodeDAO geoZipCodeDAO;
	
	private ProviderDAO providerDAO;
	
	private UserDAO userDAO;
	
	private BaAlertDAO baAlertDAO;
	
	private BaAlertSentLogDAO baAlertSentLogDAO;
	
	private BeDAOImpl beDAO;
	
	private DsqBillCommentDAOImpl dsqBillCommentDAO;
	
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}
	
	public AccountDAO getAccountDAO() {
		if (accountDAO == null) {
			accountDAO = new AccountDAOImpl();
		}
		return accountDAO;
	}
	
	public BillDAO getBillDAO() {
		if (billDAO == null) {
			billDAO = new BillDAOImpl();
		}
		return billDAO;
	}
	
	public GeoZipCodeDAO getGeoZipCodeDAO() {
		if (geoZipCodeDAO == null) {
			geoZipCodeDAO = new GeoZipCodeDAOImpl();
		}
		return geoZipCodeDAO;
	}
	
	public ProviderDAO getProviderDAO() {
		if (providerDAO == null) {
			providerDAO = new ProviderDAOImpl();
		}
		return providerDAO;
	}
	
	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAOImpl();
		}
		return userDAO;
	}
	
	public BaAlertDAO getBaAlertDAO() {
		if (baAlertDAO == null) {
			baAlertDAO = new BaAlertDAOImpl();
		}
		return baAlertDAO;
	}
	
	public BaAlertSentLogDAO getBaAlertSentLogDAO() {
		if (baAlertSentLogDAO == null) {
			baAlertSentLogDAO = new BaAlertSentLogDAOImpl();
		}
		return baAlertSentLogDAO;
	}
	
	public BeDAOImpl getBeDAO() {
		if (beDAO == null) {
			beDAO = new BeDAOImpl();
		}
		return beDAO;
	}
	
	public DsqBillCommentDAOImpl getDsqBillCommentDAO() {
		if (dsqBillCommentDAO == null) {
			dsqBillCommentDAO = new DsqBillCommentDAOImpl();
		}
		return dsqBillCommentDAO;
	}
}
