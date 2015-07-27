package me.mos.lnk.utils;

import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年3月24日 下午2:04:36
 */
public class DES {

	private static final byte[] IV_SPEC = { 0, 0, 0, 0, 0, 0, 0, 0 };

	public static void main(String[] args) throws Exception {
//		byte[] key = "4E0FQ1C1G01L4W79".getBytes();
//		byte[] key = SecurityKeyGenerator.keyGenerator(64);
		byte[] key = Base64.decodeBase64("H8cO8RaYmxo=".getBytes(Charsets.UTF_8));
		System.out.println(key);
		String e = encrypt("liufei", key);
		System.out.println("e : " + e);
		String d = decrypt(e, key);
		System.out.println("d : " + d);
	}

	public static String encrypt(String content, byte[] key) throws GeneralSecurityException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desKey = new DESKeySpec(key);
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		AlgorithmParameterSpec ivspec = new IvParameterSpec(IV_SPEC);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, ivspec);
		return new String(Base64.encodeBase64(cipher.doFinal(content.getBytes(Charsets.UTF_8))), Charsets.UTF_8);
	}

	public static String decrypt(String content, byte[] key) throws GeneralSecurityException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desKey = new DESKeySpec(key);
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		AlgorithmParameterSpec ivspec = new IvParameterSpec(IV_SPEC);
		cipher.init(Cipher.DECRYPT_MODE, securekey, ivspec);
		return new String(cipher.doFinal(Base64.decodeBase64(content.getBytes(Charsets.UTF_8))), Charsets.UTF_8);
	}
}