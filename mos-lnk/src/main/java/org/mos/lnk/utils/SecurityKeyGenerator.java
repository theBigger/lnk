package org.mos.lnk.utils;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author 刘飞
 * 
 * @version 1.0.0
 * @since 2015年3月24日 下午3:44:42
 */
public class SecurityKeyGenerator {

	public static void main(String[] args) throws GeneralSecurityException {
		System.out.println(keyEncryptGenerator(64));
	}

	public static String keyEncryptGenerator(int keySize) throws GeneralSecurityException {
		return new String(Base64.encodeBase64(keyGenerator(keySize)), Charsets.UTF_8);
	}

	public static byte[] keyGenerator(int keySize) throws GeneralSecurityException {
		SecureRandom secureRandom = new SecureRandom();
		byte[] keyData = new byte[keySize / 8];
		secureRandom.nextBytes(keyData);
		if (keySize == 64) {
			DESKeySpec keySpec = new DESKeySpec(keyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key key = keyFactory.generateSecret(keySpec);
			return key.getEncoded();
		} else if (keySize == 128) {
			byte[] tripKeyData = new byte[24];
			System.arraycopy(keyData, 0, tripKeyData, 0, 16);
			System.arraycopy(keyData, 0, tripKeyData, 16, 8);
			keyData = tripKeyData;

			DESedeKeySpec keySpec = new DESedeKeySpec(keyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			Key key = keyFactory.generateSecret(keySpec);

			keyData = new byte[keySize / 8];
			System.arraycopy(key.getEncoded(), 0, keyData, 0, 16);
			return keyData;
		} else if (keySize == 192) {
			DESedeKeySpec keySpec = new DESedeKeySpec(keyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			Key key = keyFactory.generateSecret(keySpec);
			return key.getEncoded();
		} else {
			throw new RuntimeException("Unsupported keySize=[" + keySize + "].");
		}
	}
}