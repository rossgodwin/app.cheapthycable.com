package com.gwn.xcbl.bl.auth.signup;

import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gwn.xcbl.bl.account.AccountHelper;
import com.gwn.xcbl.bl.account.UserHelper;
import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.bl.mail.EmailFromAddressFactory;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.bl.thymeleaf.TlfUtils;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.SignupDTO;
import com.gwn.xcbl.web.AppServletContextUtils;
import com.gwn.xcbl.web.HttpServletRequestHelper;

public class SignupHelper {

	/**
	 * Assume everything with signup object has been validated.
	 * 
	 * @param signup
	 * @return
	 */
	public static User setupAccount(SignupDTO signup) {
		Account account = AccountHelper.createAccount();
		User user = UserHelper.createUser(account, signup.getEmail(), signup.getPassword(), signup.getEmail());
		return user;
	}
	
	public static Email buildEmail(HttpServletRequest httpRequest, User user) throws URISyntaxException {
		ServletContext servletCtx = httpRequest.getServletContext();
		
		TemplateEngine engine = TlfUtils.getTemplateEngine(servletCtx);
		
		String baseUrl = HttpServletRequestHelper.getServerContextPath(httpRequest).toString();
		String logoRealPath = AppServletContextUtils.getLogoRealPath(servletCtx);
		String logoFileName = FilenameUtils.getName(logoRealPath);
		
		Context ctx = new Context();
		TlfUtils.addVariableAppName(ctx);
		TlfUtils.addVariableLogoImg(ctx, logoFileName);
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_URL, baseUrl);
		TlfUtils.addVariableSocialMediaUrls(ctx);
		
		String htmlContent = engine.process("emails/signup-welcome.html", ctx);
		
		Email email = new Email();
		email.setFrom(EmailFromAddressFactory.getNoReplyAddress());
		email.getRecipients().add(new EmailRecipient(null, user.getEmail()));
		email.setSubject("Welcome To " + AppConstants.APP_NAME);
		email.setHtmlMsg(htmlContent);
		email.setLogoBodyPart(new EmailBodyPart("inline", logoRealPath, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return email;
	}
}
