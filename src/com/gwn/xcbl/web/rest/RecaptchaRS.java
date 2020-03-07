package com.gwn.xcbl.web.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.gwn.xcbl.bl.google.recaptcha.RecaptchaApiResourceSiteVerify;
import com.gwn.xcbl.bl.google.recaptcha.RecaptchaApiSiteVerify;
import com.gwn.xcbl.bl.google.recaptcha.RecaptchaHelper;
import com.gwn.xcbl.bl.http.HttpUtils;
import com.gwn.xcbl.data.model.AppData;
import com.gwn.xcbl.data.shared.RecaptchaVerifyResultDTO;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;

@Path("/recaptcha")
public class RecaptchaRS {
	
	@POST
	@Path("/verify")
	@Produces({MediaType.APPLICATION_JSON})
	public Response verify(
			@FormParam(ReqParams.PARAM0) String token,
			@FormParam(ReqParams.PARAM1) String action) {
		
		
		RecaptchaVerifyResultDTO dto = new RecaptchaVerifyResultDTO();
		try {
			String url = new RecaptchaApiResourceSiteVerify().getSiteVerifyUrl(AppData.getInstance().getRecaptchaSecretKey(), token);
			
			String json = HttpUtils.callPost(url);
			
			RecaptchaApiSiteVerify verify = new Gson().fromJson(json, RecaptchaApiSiteVerify.class);
			
			if (RecaptchaHelper.isSafe(action, verify)) {
				dto.setSafe(true);
			} else {
				dto.setSafe(false);
				dto.setMessage("Suspicious behavior detected. Please try again later.");
			}
		} catch (Exception e) {
			dto.setSafe(true);
		}
		
		ResponseDTO<RecaptchaVerifyResultDTO> response = new ResponseDTO<RecaptchaVerifyResultDTO>(dto);
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
}
