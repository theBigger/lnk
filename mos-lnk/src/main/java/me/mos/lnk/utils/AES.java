package me.mos.lnk.utils;

import java.security.GeneralSecurityException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年3月24日 下午3:38:57
 */
public class AES {

	private static final String AES = "AES";

	public static String encrypt(String content, byte[] key) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES);
		Key secretKey = new SecretKeySpec(key, AES);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return new String(Base64.encodeBase64(cipher.doFinal(content.getBytes(Charsets.UTF_8))), Charsets.UTF_8);
	}

	public static String decrypt(String content, byte[] key) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(AES);
		Key secretKey = new SecretKeySpec(key, AES);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(Base64.decodeBase64(content.getBytes(Charsets.UTF_8))), Charsets.UTF_8);
	}

}