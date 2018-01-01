package com.gwn.xcbl.web.servlet;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwn.xcbl.bl.auth.signup.SignupHelper;
import com.gwn.xcbl.bl.auth.signup.SignupVerifiedUrlIntf;
import com.gwn.xcbl.bl.auth.signup.SignupVerifySrvltIntf;
import com.gwn.xcbl.bl.mail.Emailer;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.common.AppUris;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.web.HttpServletRequestHelper;

@WebServlet(name="SignupVerify", urlPatterns={"/" + AppUris.ROOT_PATH_NAME + "/srv/SignupVerify"})
public class SignupVerifySrvlt extends HttpServlet implements SignupVerifySrvltIntf {

	private static final long serialVersionUID = -91525155276141486L;
	
	private static Log log = LogFactory.getLog(SignupVerifySrvlt.class);
	
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
		
		user.setValidated(true);
		HibernateUtil.getSessionFactory().getCurrentSession().update(user);
		
//		HttpStatus.
//		HttpStatus.SC_FORBIDDEN
//		HttpStatus.SC_UNAUTHORIZED
//		response.setStatus(arg0);
		
//		request.getRequestDispatcher("Login.jsp").forward(request, response);
		
		try {
			Email email = SignupHelper.buildEmail(request, user);
			Emailer.sendEmail(email);
		} catch (Exception e) {
			log.error(e);
		}
		
		String baseUrl = HttpServletRequestHelper.getServerContextPath(request).toString();
		String url = baseUrl + "/" + SignupVerifiedUrlIntf.API_URL;
		response.sendRedirect(url);
	}
}
