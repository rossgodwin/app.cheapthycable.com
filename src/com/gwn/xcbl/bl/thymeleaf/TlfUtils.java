package com.gwn.xcbl.bl.thymeleaf;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.common.AppConstants;

/**
 * http://mvnrepository.com/artifact/org.thymeleaf/thymeleaf/3.0.1.RELEASE
 * http://www.thymeleaf.org/doc/articles/springmail.html
 * 
 */
public class TlfUtils {

	public static TemplateEngine getTemplateEngine(HttpServletRequest httpRequest) {
		ServletContext servletCtx = httpRequest.getServletContext();
		TemplateEngine engine = getTemplateEngine(servletCtx);
		return engine;
	}
	
	public static TemplateEngine getTemplateEngine(ServletContext servletContext) {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
		resolver.setTemplateMode("HTML");
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setCacheTTLMs(3600000L);
		
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(resolver);
		return engine;
	}
	
	public static void addVariableAppName(Context ctx) {
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_NAME, AppConstants.APP_NAME);
	}
	
	public static void addVariableLogoImg(Context ctx, String logoFileName) {
		ctx.setVariable(EmailConstants.VARIABLE_LOGO_IMG_SRC, "cid:" + logoFileName);
	}
	
	public static void addVariableSocialMediaUrls(Context ctx) {
		ctx.setVariable(EmailConstants.VARIABLE_TWITTER_HOME_PAGE_URL, AppConstants.TWITTER_HOME_PAGE_URL);
		ctx.setVariable(EmailConstants.VARIABLE_FB_HOME_PAGE_URL, AppConstants.FB_HOME_PAGE_URL);
	}
}
