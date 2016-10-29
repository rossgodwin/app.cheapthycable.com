package com.gwn.xcbl.bl.auth.pwd.reset;

import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.utils.URIBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.bl.mail.EmailFromAddressFactory;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.bl.thymeleaf.TlfUtils;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.web.AppServletContextUtils;
import com.gwn.xcbl.web.HttpServletRequestHelper;

public class ResetPwdHlpr {

	public static String getResetUrl(HttpServletRequest httpRequest, User user) throws URISyntaxException {
		String baseUrl = HttpServletRequestHelper.getServerContextPath(httpRequest).toString();
		URIBuilder bldr = new URIBuilder(baseUrl + "/" + ResetPwdUrlIntf.API_URL);
		bldr.setParameters(ResetPwdUrlIntf.Util.getParams(user));
		String rslt = bldr.toString();
		return rslt;
	}
	
	public static Email buildEmail(HttpServletRequest httpRequest, User user) throws URISyntaxException {
		ServletContext servletCtx = httpRequest.getServletContext();
		
		TemplateEngine engine = TlfUtils.getTemplateEngine(servletCtx);
		
		String logoRealPath = AppServletContextUtils.getLogoRealPath(servletCtx);
		String logoFileName = FilenameUtils.getName(logoRealPath);
		
		Context ctx = new Context();
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_NAME, AppConstants.APP_NAME);
		ctx.setVariable(EmailConstants.VARIABLE_LOGO_IMG_SRC, "cid:" + logoFileName);
		
		String htmlContent = engine.process("emails/pwd-reset-notify.html", ctx);
		
		Email email = new Email();
		email.setFrom(EmailFromAddressFactory.getNoReplyAddress());
		email.getRecipients().add(new EmailRecipient(null, user.getEmail()));
		email.setSubject("Your Password Has Been Changed");
		email.setHtmlMsg(htmlContent);
		email.setLogoBodyPart(new EmailBodyPart("inline", logoRealPath, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return email;
	}
}
