package com.gwn.xcbl.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.PagingResultDTO;
import com.gwn.xcbl.data.shared.ProviderDTO;
import com.gwn.xcbl.data.shared.ProviderSearchCritrDTO;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.transformer.ProviderDtoTransformer;

@Path("/provider")
public class ProviderRS extends BaseRS {

	@GET
	@Path("/list")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getProviderList(
			@QueryParam(ReqParams.PARAM0) String searchCritr,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			ProviderSearchCritrDTO critr = new ProviderSearchCritrDTO();
			critr.setName(searchCritr);
			
			List<Provider> objs = DAOFactory.getInstance().getProviderDAO().findByCritr(critr, null, null);
			List<ProviderDTO> dtos = (List<ProviderDTO>) CollectionUtils.collect(objs, new ProviderDtoTransformer());
			
			ResponseDTO<List<ProviderDTO>> response = new ResponseDTO<List<ProviderDTO>>(dtos);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/list/page")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getProviderListPage(
			@QueryParam(ReqParams.PARAM0) String searchCritr,
			@QueryParam(ReqParams.PARAM1) Integer offset,
			@QueryParam(ReqParams.PARAM2) Integer limit,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			ProviderSearchCritrDTO critr = new ProviderSearchCritrDTO();
			critr.setName(searchCritr);
			
			List<Provider> objs = DAOFactory.getInstance().getProviderDAO().findByCritr(critr, offset, limit);
			int total = DAOFactory.getInstance().getProviderDAO().countByCritr(critr);
			List<ProviderDTO> dtos = (List<ProviderDTO>) CollectionUtils.collect(objs, new ProviderDtoTransformer());
			
			ResponseDTO<PagingResultDTO<ProviderDTO>> response = new ResponseDTO<PagingResultDTO<ProviderDTO>>(new PagingResultDTO<>(offset, limit, total, dtos));
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
