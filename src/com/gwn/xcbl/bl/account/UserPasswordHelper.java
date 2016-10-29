package com.gwn.xcbl.bl.account;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class UserPasswordHelper {

	public static String encryptPassword(String plainPassword) {
		return new StrongPasswordEncryptor().encryptPassword(plainPassword);
	}
	
	public static boolean checkPassword(String plainPassword, String encryptedPassword) {
		boolean r = new StrongPasswordEncryptor().checkPassword(plainPassword, encryptedPassword);
		return r;
	}
}
