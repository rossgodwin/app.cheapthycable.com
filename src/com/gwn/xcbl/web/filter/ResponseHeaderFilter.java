package com.gwn.xcbl.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ResponseHeaderFilter implements Filter {

	private FilterConfig config;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		config = arg0;
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		final String paramCacheControl = "Cache-Control";
		final String paramPragma = "Pragma";
		final String paramExpires = "Expires";
		
		HttpServletResponse resp = (HttpServletResponse) arg1;
		resp.setHeader(paramPragma, config.getInitParameter(paramPragma));
        resp.setHeader(paramCacheControl, config.getInitParameter(paramCacheControl));
        resp.setHeader(paramExpires, config.getInitParameter(paramExpires));
        
        arg2.doFilter(arg0, arg1);
	}
	
	@Override
	public void destroy() {
	}
}
