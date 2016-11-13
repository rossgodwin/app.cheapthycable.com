package com.gwn.xcbl.web.servlet;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.bl.ba.BaAlertUnsubscribeSrvltIntf;
import com.gwn.xcbl.bl.ba.BaAlertUnsubscribeSuccessfulUrlIntf;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.web.HttpServletRequestHelper;

public class BaAlertUnsubscribeSrvlt extends HttpServlet implements BaAlertUnsubscribeSrvltIntf {

	private static final long serialVersionUID = -5038171253440144352L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter(PARAM_TOKEN);
		if (StringUtils.isEmpty(token)) {
			throw new InvalidParameterException("Parameter {" + PARAM_TOKEN + "} missing");
		}
		
		Long alertId = Util.decrypt(token);
		if (alertId == null) {
			throw new InvalidParameterException("Parameter {" + PARAM_TOKEN + "} is invalid");
		}
		
		BaAlert alert = DAOFactory.getInstance().getBaAlertDAO().findById(alertId, false);
		if (alert == null) {
			throw new InvalidParameterException("Parameter {" + PARAM_TOKEN + "} is invalid");
		}
		
		alert.setUnsubscribed(true);
		HibernateUtil.getSessionFactory().getCurrentSession().update(alert);
		
		String baseUrl = HttpServletRequestHelper.getServerContextPath(request).toString();
		String url = baseUrl + "/" + BaAlertUnsubscribeSuccessfulUrlIntf.API_URL;
		response.sendRedirect(url);
	}
}
