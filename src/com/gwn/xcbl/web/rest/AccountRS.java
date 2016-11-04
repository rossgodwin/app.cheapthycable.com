package com.gwn.xcbl.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ResponseDTO;

@Path("/account")
public class AccountRS extends BaseRS {

	@GET
	@Path("/receiveLowerBillAlerts")
	@Produces({MediaType.APPLICATION_JSON})
	public Response receiveLowerBillAlerts(
			@Context HttpServletRequest httpRequest) {
		try {
			UserPrincipal principal = authenticate(httpRequest);
			
			User user = DAOFactory.getInstance().getUserDAO().findByUsername(principal.getUsername());
			
			ResponseDTO<Boolean> response = new ResponseDTO<Boolean>(user.getAccount().isReceiveLowerBillAlerts());
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/billAlertRegister")
	@Produces({MediaType.APPLICATION_JSON})
	public Response billAlertRegister(
			@FormParam("email") String email,
			@Context HttpServletRequest httpRequest) {
		try {
			UserPrincipal principal = authenticate(httpRequest);
			
			ResponseDTO<Void> response;
			
			List<String> errs = new ArrayList<String>();
			if (StringUtils.isEmpty(email)) {
				errs.add("Email is required.");
			}
			
			if (errs.size() == 0) {
				User user = DAOFactory.getInstance().getUserDAO().findById(principal.getUserId(), false);
				if (!user.getEmail().equals(email)) {
					user.setEmail(email);
					HibernateUtil.getSessionFactory().getCurrentSession().update(user);
				}
				
				user.getAccount().setReceiveLowerBillAlerts(true);
				HibernateUtil.getSessionFactory().getCurrentSession().update(user.getAccount());
				
				response = new ResponseDTO<Void>(ResponseDTO.RESULT_OK);
			} else {
				response = new ResponseDTO<Void>(ResponseDTO.RESULT_FAIL);
				response.setErrs(errs);
			}
			
			String json = new Gson().toJson(response);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
