package com.gwn.xcbl.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.shared.geo.GeoZipCodeDTO;
import com.gwn.xcbl.data.transformer.geo.GeoZipCodeDtoTransformer;

@Path("geoZipCode")
public class GeoZipCodeRS extends BaseRS {

	@GET
	@Path("/zipCode/{zipCode}/validate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response isZipCodeValid(
			@PathParam("zipCode") String zipCode,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			boolean valid = DAOFactory.getInstance().getGeoZipCodeDAO().countByZipCode(zipCode) > 0;
			
			ResponseDTO<Boolean> response = new ResponseDTO<Boolean>(valid);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/zipCode/{zipCode}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getGeoZipCode(
			@PathParam("zipCode") String zipCode,
			@Context HttpServletRequest httpRequest) {
		try {
			authenticate(httpRequest);
			
			GeoZipCodeDTO dto = null;
			List<GeoZipCode> objs = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(zipCode);
			if (objs != null && objs.size() > 0) {
				dto = new GeoZipCodeDtoTransformer().transform(objs.get(0));
			}
			
			ResponseDTO<GeoZipCodeDTO> response = new ResponseDTO<GeoZipCodeDTO>(dto);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
