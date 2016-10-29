package com.gwn.xcbl.bl.thymeleaf;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

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
//		resolver.setSuffix(".html");
		resolver.setCacheTTLMs(3600000L);
		
		TemplateEngine engine = new TemplateEngine();
		engine.setTemplateResolver(resolver);
		return engine;
	}
}
