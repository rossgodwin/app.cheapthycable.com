package com.gwn.xcbl.bl.auth.signup;

import com.gwn.xcbl.data.hibernate.entity.User;

public interface SignupObserver {

	public void signup(User user);
}
