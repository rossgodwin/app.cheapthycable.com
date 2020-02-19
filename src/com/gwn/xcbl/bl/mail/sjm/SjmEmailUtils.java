package com.gwn.xcbl.bl.mail.sjm;

import javax.activation.FileDataSource;
import javax.servlet.ServletContext;

import org.simplejavamail.email.Email;

import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.web.AppServletContextUtils;

public class SjmEmailUtils {

	public static void setDefaultFromAddress(Email email) {
//		email.setFromAddress(AppConstants.APP_NAME, "<put email address here>");
	}
	
	public static void embedLogo(Email email, ServletContext servletCtx) {
		email.addEmbeddedImage(EmailConstants.VARIABLE_LOGO, new FileDataSource(AppServletContextUtils.getLogoRealPath(servletCtx)));
	}
}
