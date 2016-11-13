package com.gwn.xcbl.bl;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.util.text.StrongTextEncryptor;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
	
	public static String simpleLongEncrypt(long id) {
		Random rand = new Random((new Date()).getTime());
		
		BASE64Encoder encoder = new BASE64Encoder();
		byte[] salt = new byte[8];
		rand.nextBytes(salt);
		return encoder.encode(salt) + encoder.encode(String.valueOf(id).getBytes());
	}
	
	public static Long simpleLongDecrypt(String encStr) {
		if (encStr.length() > 12) {
			String cipher = encStr.substring(12);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				String idStr = new String(decoder.decodeBuffer(cipher));
				return Long.parseLong(idStr);
			} catch (IOException e) {
			}
		}
		return null;
	}
}
