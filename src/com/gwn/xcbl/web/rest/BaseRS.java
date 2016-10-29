package com.gwn.xcbl.web.rest;

import javax.servlet.http.HttpServletRequest;

import com.gwn.xcbl.common.UserPrincipal;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.model.AuthenticationException;

public class BaseRS {

	protected UserPrincipal authenticate(HttpServletRequest request) throws AuthenticationException {
		UserPrincipal principal = (UserPrincipal)request.getUserPrincipal();
		if (principal == null) {
			throw new AuthenticationException("Not logged in");
		}
		return principal;
	}
	
//	protected UserPrincipal authenticate(HttpServletRequest request, UserRole... roles) {
//		UserPrincipal principal = (UserPrincipal)request.getUserPrincipal();
//		if (principal == null) {
//			throw new AuthenticationException("Not logged in");
//		} else {
//			boolean found = false;
//			for (int i = 0; i < roles.length; i++) {
//				if (principal.getRole().equals(roles[i])) {
//					found = true;
//					break;
//				}
//			}
//			if (!found) {
//				throw new PermissionDeniedException("Permission denied");
//			}
//		}
//		return principal;
//	}
	
	protected User getAuthUser(HttpServletRequest request) throws AuthenticationException {
		UserPrincipal principal = authenticate(request);
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(principal.getUsername());
		return user;
	}
	
	protected long getAuthAccountId(HttpServletRequest request) throws AuthenticationException {
		User user = getAuthUser(request);
		return user.getAccount().getId();
	}
}
