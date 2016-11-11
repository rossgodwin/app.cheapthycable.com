package com.gwn.xcbl.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.gwn.xcbl.bl.ba.BaAlertHelper;
import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.ba.BaAlertDAOImpl;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.PagingResultDTO;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.shared.ba.BaAlertDTO;
import com.gwn.xcbl.data.transformer.ba.BaAlertDtoTransformer;

@Path("/baAlert")
public class BaAlertRS extends BaseRS {

	@GET
	@Path("/hasAlert")
	@Produces({MediaType.APPLICATION_JSON})
	public Response hasAlert(
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			int count = new BaAlertDAOImpl().countEmailAlertsByAccount(accountId);
			
			ResponseDTO<Boolean> response = new ResponseDTO<Boolean>(count > 0);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/addDefaultAlert")
	@Produces({MediaType.APPLICATION_JSON})
	public Response addDefaultAlert(
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
				
				BaAlertHelper.addDefaultAlert(user.getAccount());
				
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
	
	@GET
	@Path("/my/list/page")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getMyAlertListPage(
			@QueryParam(ReqParams.PARAM0) Integer offset,
			@QueryParam(ReqParams.PARAM1) Integer limit,
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			List<BaAlert> objs = DAOFactory.getInstance().getBaAlertDAO().findAccountAlerts(accountId, offset, limit);
			int total = DAOFactory.getInstance().getBaAlertDAO().countAccountAlerts(accountId);
			List<BaAlertDTO> dtos = (List<BaAlertDTO>) CollectionUtils.collect(objs, new BaAlertDtoTransformer());
			
			ResponseDTO<PagingResultDTO<BaAlertDTO>> response = new ResponseDTO<PagingResultDTO<BaAlertDTO>>(new PagingResultDTO<>(offset, limit, total, dtos));
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/{alertId}/delete")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deleteAlert(
			@PathParam("alertId") Long alertId,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			// TODO create facade to validate user has permission to delete alert
			BaAlert dbo = DAOFactory.getInstance().getBaAlertDAO().findById(alertId, false);
			
			HibernateUtil.getSessionFactory().getCurrentSession().delete(dbo);
			
			ResponseDTO<Void> response = new ResponseDTO<Void>(ResponseDTO.RESULT_OK);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/saveOrUpdate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveOrUpdateAlert(
			@FormParam(ReqParams.PARAM0) String alertJson,
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			Account account = DAOFactory.getInstance().getAccountDAO().findById(accountId, true);
			
			BaAlertDTO dto = new Gson().fromJson(alertJson, BaAlertDTO.class);
			
			if (!ILongId.Utils.isPersistent(dto)) {
				BaAlertHelper.save(account, dto);
			} else {
				// TODO handle update
			}
			
			ResponseDTO<BaAlertDTO> response = new ResponseDTO<BaAlertDTO>(dto);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
