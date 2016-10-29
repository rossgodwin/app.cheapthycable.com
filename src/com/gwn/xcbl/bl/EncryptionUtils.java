package com.gwn.xcbl.bl;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptionUtils {

	private static final String EncKey = "78a2R!9#";

	public static String encryptBase64DefKey(String plain) {
		return encryptBase64(EncKey, plain);
	}

	public static String decryptBase64DefKey(String encrypted) {
		return decryptBase64(EncKey, encrypted);
	}

	public static String encryptBase64(String key, String plain) {
		StrongTextEncryptor encryptor = new StrongTextEncryptor();
		encryptor.setPassword(key);
		String r = encryptor.encrypt(plain);
		r = new String(new Base64().encode(r.getBytes()));
		return r;
	}

	public static String decryptBase64(String key, String encrypted) {
		StrongTextEncryptor encryptor = new StrongTextEncryptor();
		encryptor.setPassword(key);
		String r = encryptor.decrypt(new String(new Base64().decode(encrypted.getBytes())));
		return r;
	}
}
