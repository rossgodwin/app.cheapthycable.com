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
import com.gwn.xcbl.bl.bill.dsq.DsqBillPostHlpr;
import com.gwn.xcbl.bl.bill.dsq.DsqBillPostNotifyRun;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;

@Path("dsqBillPost")
public class DsqBillPostRS extends BaseRS {

	@POST
	@Path("/create")
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(
			@FormParam(ReqParams.PARAM0) Long billId,
			@FormParam(ReqParams.PARAM1) Long dsqPostId,
			@Context HttpServletRequest httpRequest) {
		try {
			User user = getAuthUser(httpRequest);
			
			DsqBillPost billPost = DsqBillPostHlpr.create(user.getAccount().getId(), billId, dsqPostId);
			
			DsqBillPostNotifyRun run = new DsqBillPostNotifyRun(httpRequest.getServletContext(), user.getId(), billPost);
			Thread t = new Thread(run);
			t.start();
			
			ResponseDTO<Void> response = new ResponseDTO<Void>();
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
