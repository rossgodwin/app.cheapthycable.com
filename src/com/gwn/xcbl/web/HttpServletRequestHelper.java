package com.gwn.xcbl.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.model.AppData;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.shared.UserDTO;
import com.gwn.xcbl.data.transformer.SafeUserDtoTransformer;

public class HttpServletRequestHelper {

	public static StringBuilder getServerContextPath(HttpServletRequest request) {
		StringBuilder b = new StringBuilder();
		if (AppData.getInstance().isEnvProd()) {
			b.append(AppData.getInstance().getDomainUrl());
		} else {
			b.append(request.getScheme()).append("://");
			b.append(request.getServerName()).append(":").append(request.getServerPort());
			b.append(request.getContextPath());
		}
		return b;
	}
	
	public static UserPrincipal getUserPrincipal(HttpServletRequest request) {
		UserPrincipal principal = (UserPrincipal) request.getUserPrincipal();
		return principal;
	}
	
	public static User getUser(HttpServletRequest request) {
		String username = getUserPrincipal(request).getUsername();
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(username);
		return user;
	}
	
	public static Account getAccount(HttpServletRequest request) {
		User user = getUser(request);
		return user.getAccount();
	}
	
	/**
	 * Make sure request is authenticated.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void writeUserJsonResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User obj = getUser(request);
		UserDTO dto = new SafeUserDtoTransformer().transform(obj);
		String json = new Gson().toJson(new ResponseDTO<UserDTO>(dto));
		writeJsonResponse(response, json);
	}
	
	public static void writeJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}
