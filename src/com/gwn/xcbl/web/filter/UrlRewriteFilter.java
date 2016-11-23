package com.gwn.xcbl.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.gwn.xcbl.bl.bill.BillCommentsUrlIntf;
import com.gwn.xcbl.data.model.AppData;

public class UrlRewriteFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		String reqUri = req.getRequestURI();
		
		if (reqUri.matches(BillCommentsUrlIntf.URL_REGEX_PATTERN)) {
			String newUri = AppData.getInstance().getAppSecureEp();
			req.getRequestDispatcher(newUri).forward(req, arg1);
		}

		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void destroy() {
	}
}
