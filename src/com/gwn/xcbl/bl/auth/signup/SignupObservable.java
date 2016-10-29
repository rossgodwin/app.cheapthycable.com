package com.gwn.xcbl.bl.auth.signup;

import java.util.ArrayList;
import java.util.Collection;

import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.SignupDTO;

public class SignupObservable {

	private Collection<SignupObserver> observers = new ArrayList<SignupObserver>();
	
	public SignupObservable() {
	}
	
	public SignupObservable(SignupObserver observer) {
		observers.add(observer);
	}
	
	public User signup(SignupDTO signup) {
		User user = SignupHelper.setupAccount(signup);
		notifyObservers(user);
		return user;
	}
	
	public void addObserver(SignupObserver o) {
		observers.add(o);
	}
	
	private void notifyObservers(User user) {
		for (SignupObserver observer : observers) {
			observer.signup(user);
		}
	}
}
