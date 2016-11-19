package com.gwn.xcbl.web.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.gwn.xcbl.bl.bill.dsq.DsqBillCommentHlpr;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;

@Path("dsqBillComment")
public class DsqBillCommentRS extends BaseRS {

	@POST
	@Path("/create")
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(
			@FormParam(ReqParams.PARAM0) Long billId,
			@FormParam(ReqParams.PARAM1) Long dsqCommentId,
			@Context HttpServletRequest httpRequest) {
		try {
			long accountId = getAuthAccountId(httpRequest);
			
			DsqBillCommentHlpr.create(accountId, billId, dsqCommentId);
			
			ResponseDTO<Void> response = new ResponseDTO<Void>();
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
