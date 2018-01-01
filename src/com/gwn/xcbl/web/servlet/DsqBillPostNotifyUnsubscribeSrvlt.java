package com.gwn.xcbl.web.servlet;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.bl.bill.dsq.post.DsqBillPostNotifyUnsubscribeSrvltIntf;
import com.gwn.xcbl.bl.bill.dsq.post.DsqBillPostNotifyUnsubscribeSuccessfulUrlIntf;
import com.gwn.xcbl.common.AppUris;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.web.HttpServletRequestHelper;

@WebServlet(name="DsqBillPostNotifyUnsubscribe", urlPatterns={"/" + AppUris.ROOT_PATH_NAME + "/srv/DsqBillPostNotifyUnsubscribe"})
public class DsqBillPostNotifyUnsubscribeSrvlt extends HttpServlet implements DsqBillPostNotifyUnsubscribeSrvltIntf {
	
	private static final long serialVersionUID = 4707674280700646059L;

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
		
		Long userId = Util.decrypt(token);
		if (userId == null) {
			throw new InvalidParameterException("Parameter {" + PARAM_TOKEN + "} is invalid");
		}
		
		User user = DAOFactory.getInstance().getUserDAO().findById(userId, false);
		if (user == null) {
			throw new InvalidParameterException("Parameter {" + PARAM_TOKEN + "} is invalid");
		}
		
		user.getAccount().setDsqBillPostNotify(false);
		HibernateUtil.getSessionFactory().getCurrentSession().update(user.getAccount());
		
		String baseUrl = HttpServletRequestHelper.getServerContextPath(request).toString();
		String url = baseUrl + "/" + DsqBillPostNotifyUnsubscribeSuccessfulUrlIntf.API_URL;
		response.sendRedirect(url);
	}
}
