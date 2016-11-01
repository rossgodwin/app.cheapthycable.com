package com.gwn.xcbl.bl.auth.signup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.bl.user.PwdValidator;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.shared.SignupDTO;

public class SignupValidator {

	public List<String> isValid(SignupDTO signup) {
		List<String> errs = new ArrayList<String>();
		if (!chkEmpty(errs, signup)) {
			return errs;
		}
		chkUniqueEmail(errs, signup);
		chkPwds(errs, signup);
		return errs;
	}
	
	protected boolean chkEmpty(List<String> errs, SignupDTO signup) {
		List<String> myErrs = new ArrayList<String>();
		if (StringUtils.isEmpty(signup.getEmail())) {
			myErrs.add("Email is required.");
		}
		if (StringUtils.isEmpty(signup.getPassword())) {
			myErrs.add("Password is required.");
		}
		if (StringUtils.isEmpty(signup.getPasswordConfirm())) {
			myErrs.add("Password confirm is required.");
		}
		errs.addAll(myErrs);
		return myErrs.size() == 0;
	}
	
	protected boolean chkUniqueEmail(List<String> errs, SignupDTO signup) {
		List<String> myErrs = new ArrayList<String>();
		if (DAOFactory.getInstance().getUserDAO().findByUsername(signup.getEmail()) != null) {
			myErrs.add("Email is already being used.");
		}
		errs.addAll(myErrs);
		return myErrs.size() == 0;
	}
	
	protected boolean chkPwds(List<String> errs, SignupDTO signup) {
		List<String> myErrs = new ArrayList<String>();
		if (!signup.getPassword().equals(signup.getPasswordConfirm())) {
			myErrs.add("Passwords do not match.");
		}
		if (myErrs.size() == 0) {
			myErrs.addAll(new PwdValidator().validate(signup.getPassword()));
		}
		errs.addAll(myErrs);
		return myErrs.size() == 0;
	}
}
