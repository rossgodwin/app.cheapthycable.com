package com.gwn.xcbl.web;

import javax.servlet.ServletContext;

public class AppServletContextUtils {

	public static String getLogoRealPath(ServletContext ctx) {
		String result = ctx.getRealPath("/client/assets/img/client_logo_rectangle_xs.png");
		return result;
	}
}
