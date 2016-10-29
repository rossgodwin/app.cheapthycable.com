package com.gwn.xcbl.web.rest;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.gwn.xcbl.bl.account.UserHelper;
import com.gwn.xcbl.bl.account.UserLoginHelper;
import com.gwn.xcbl.bl.auth.pwd.forgot.ForgotPwdHlpr;
import com.gwn.xcbl.bl.auth.pwd.reset.ResetPwdHlpr;
import com.gwn.xcbl.bl.auth.pwd.reset.ResetPwdUrlIntf;
import com.gwn.xcbl.bl.auth.signup.SignupHelper;
import com.gwn.xcbl.bl.auth.signup.SignupValidator;
import com.gwn.xcbl.bl.auth.signup.SignupVerifyHlpr;
import com.gwn.xcbl.bl.mail.Emailer;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.user.PwdValidator;
import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.model.AuthenticationException;
import com.gwn.xcbl.data.shared.ReqParams;
import com.gwn.xcbl.data.shared.ResponseDTO;
import com.gwn.xcbl.data.shared.SignupDTO;
import com.gwn.xcbl.data.shared.UserDTO;
import com.gwn.xcbl.data.transformer.SafeUserDtoTransformer;
import com.gwn.xcbl.web.HttpServletRequestHelper;

@Path("/auth")
public class AuthRS extends BaseRS {

	private static Log log = LogFactory.getLog(AuthRS.class);
	
	@POST
	@Path("/signup/uniqueEmail")
	@Produces({MediaType.APPLICATION_JSON})
	public Response signupUniqueEmail(
			@FormParam(ReqParams.PARAM0) String email) {
		ResponseDTO<Boolean> response = new ResponseDTO<Boolean>();
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(email);
		response.setResult(user == null);
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/pwd/chk")
	@Produces({MediaType.APPLICATION_JSON})
	public Response isPwdValidChk(
			@FormParam("pwd") String pwd) {
		ResponseDTO<List<String>> response = new ResponseDTO<List<String>>();
		response.setResult(new PwdValidator().validate(pwd));
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
//	@POST
//	@Path("/signup")
//	@Produces({MediaType.APPLICATION_JSON})
//	public Response signup(
//			@FormParam(ReqParams.PARAM0) String signupJson,
//			@Context HttpServletRequest httpRequest) {
//		SignupDTO signup = new Gson().fromJson(signupJson, SignupDTO.class);
//		
//		ResponseDTO<UserDTO> response;
//		
//		List<String> errs = new SignupValidator().isValid(signup);
//		if (errs.size() == 0) {
//			SignupObservable observable = new SignupObservable(new SignupObserver() {
//				
//				@Override
//				public void signup(User user) {
//					try {
//						Email email = SignupHelper.buildEmail(httpRequest, user);
//						Emailer.sendEmail(email);
//					} catch (Exception e) {
//						log.error(e);
//					}
//				}
//			});
//			User user = observable.signup(signup);
//			
//			try {
//				HibernateUtil.commit();
//				httpRequest.login(user.getUsername(), signup.getPassword());
//				response = new ResponseDTO<UserDTO>(new SafeUserDtoTransformer().transform(user));
//			} catch (ServletException e) {
//				log.error(e);
//				HibernateUtil.rollback();
//				response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_FAIL);
//				response.setErrs(Arrays.asList(new String[] {e.getMessage()}));
//			}
//		} else {
//			response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_FAIL);
//			response.setErrs(errs);
//		}
//		
//		String json = new Gson().toJson(response);
//		return Response.ok(json, MediaType.APPLICATION_JSON).build();
//	}
	@POST
	@Path("/signup")
	@Produces({MediaType.APPLICATION_JSON})
	public Response signup(
			@FormParam(ReqParams.PARAM0) String signupJson,
			@Context HttpServletRequest httpRequest) {
		SignupDTO signup = new Gson().fromJson(signupJson, SignupDTO.class);
		
		ResponseDTO<UserDTO> response;
		
		List<String> errs = new SignupValidator().isValid(signup);
		if (errs.size() == 0) {
			User user = SignupHelper.setupAccount(signup);
			
			try {
				Email email = SignupVerifyHlpr.buildEmail(httpRequest, user);
				Emailer.sendEmail(email);
				response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_OK);
			} catch (Exception e) {
				HibernateUtil.rollback();
				
				log.error(e);
				
				response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_FAIL);
				response.setErrs(Arrays.asList(new String[] {"We could not send a verification email at this time. Please try again later."}));
			}
		} else {
			response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_FAIL);
			response.setErrs(errs);
		}
		
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/login")
	@Produces({MediaType.APPLICATION_JSON})
	public Response login(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest httpRequest) {
		ResponseDTO<UserDTO> response;
		
		try {
			User user = null;
			if (httpRequest.getUserPrincipal() == null) {
				UserLoginHelper.validateLogin(username, password);
				httpRequest.login(username, password);
				
				user = HttpServletRequestHelper.getUser(httpRequest);
				if (!user.isValidated()) {
					httpRequest.getSession().invalidate();
					throw new FailedLoginException("Validation required");
				}
			} else {
				user = getAuthUser(httpRequest);
			}
			response = new ResponseDTO<UserDTO>(new SafeUserDtoTransformer().transform(user));
			return Response.ok(new Gson().toJson(response), MediaType.APPLICATION_JSON).build();
		} catch (FailedLoginException | ServletException e) {
			response = new ResponseDTO<UserDTO>(ResponseDTO.RESULT_FAIL);
			response.setErrs(Arrays.asList(e.getMessage()));
			return Response.ok(new Gson().toJson(response), MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Path("/user")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUser(
			@Context HttpServletRequest httpRequest) {
		try {
			UserPrincipal principal = authenticate(httpRequest);
			
			User obj = DAOFactory.getInstance().getUserDAO().findByUsername(principal.getUsername());
			if (obj == null) {
				throw new AuthenticationException("Could not find user.");
			}
			
			UserDTO dto = new SafeUserDtoTransformer().transform(obj);
			
			ResponseDTO<UserDTO> response = new ResponseDTO<UserDTO>(dto);
			String json = new Gson().toJson(response);
			
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} catch (AuthenticationException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/pwd/forgot")
	@Produces({MediaType.APPLICATION_JSON})
	public Response pwdForgot(
			@FormParam("email") String emailAddr,
			@Context HttpServletRequest httpRequest) {
		ResponseDTO<Void> response;
		
		User user = DAOFactory.getInstance().getUserDAO().findByEmail(emailAddr);
		if (user == null) {
			response = new ResponseDTO<Void>(ResponseDTO.RESULT_FAIL);
			response.setErrs(Arrays.asList(new String[] {"We could not find a login with that email address."}));
		} else {
			try {
				Email email = ForgotPwdHlpr.buildEmail(httpRequest, user);
				Emailer.sendEmail(email);
				response = new ResponseDTO<Void>(ResponseDTO.RESULT_OK);
			} catch (Exception e) {
				log.error(e);
				
				response = new ResponseDTO<Void>(ResponseDTO.RESULT_FAIL);
				response.setErrs(Arrays.asList(new String[] {"We could not send a password reset email at this time."}));
			}
		}
		
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/pwd/reset")
	@Produces({MediaType.APPLICATION_JSON})
	public Response pwdReset(
			@FormParam("token") String token,
			@FormParam("newPwd") String newPwd,
			@Context HttpServletRequest httpRequest) {
		ResponseDTO<Void> response = new ResponseDTO<>(ResponseDTO.RESULT_OK);
		
		Long userId = ResetPwdUrlIntf.Util.decrypt(token);
		
		User user = DAOFactory.getInstance().getUserDAO().findById(userId, false);
		UserHelper.updatePassword(user, newPwd);
		
		try {
			Email email = ResetPwdHlpr.buildEmail(httpRequest, user);
			Emailer.sendEmail(email);
		} catch (Exception e) {
			log.error(e);
		}
		
		String json = new Gson().toJson(response);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/logout")
	public Response logout(
			@Context HttpServletRequest httpRequest) {
		httpRequest.getSession().invalidate();
		return Response.ok().build();
	}
}
