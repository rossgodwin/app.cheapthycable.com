package com.gwn.xcbl.data.hibernate.dao;

import com.gwn.xcbl.data.dao.AccountDAO;
import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.dao.GeoZipCodeDAO;
import com.gwn.xcbl.data.dao.ProviderDAO;
import com.gwn.xcbl.data.dao.UserDAO;

public class DAOFactory {

	private static DAOFactory instance;
	
	private AccountDAO accountDAO;
	
	private BillDAO billDAO;
	
	private GeoZipCodeDAO geoZipCodeDAO;
	
	private ProviderDAO providerDAO;
	
	private UserDAO userDAO;
	
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
}
