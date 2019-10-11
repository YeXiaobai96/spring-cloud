package com.yofc.trace.util;


import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	public static final String algorithm = "AES";

	// 这是默认模式
	// public static final String transformation = "AES/ECB/PKCS5Padding";
	// 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV : IvParameterSpec iv = new
	// IvParameterSpec(key.getBytes());
	// public static final String transformation = "AES/CBC/PKCS5Padding";
	// NOPadding: 使用NOPadding模式时, 原文长度必须是16byte的整数倍
	public static final String transformation = "AES/CBC/PKCS5Padding";
	public static final String key = "fsdnbfskfqwertyq";
	//public static final String original = "";

	public static void main(String[] args) throws Exception {

		String encryptByAES = encryptByAES("2018120212032585");
		System.out.println(encryptByAES);
		String decryptByAES = decryptByAES(encryptByAES);
		System.out.println(decryptByAES);

	}

	public static String encryptByAES(String original) throws Exception {

		// 获取Cipher
		Cipher cipher = Cipher.getInstance(transformation);
		// 生成密钥
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		// 指定模式(加密)和密钥
		// 创建初始化向量
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		// cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		// 加密
		byte[] bytes = cipher.doFinal(original.getBytes());

		return Base64.getEncoder().encodeToString(bytes);
	}

	public static String decryptByAES(String encrypted) throws Exception {

		// 获取Cipher
		Cipher cipher = Cipher.getInstance(transformation);
		// 生成密钥
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		// 指定模式(解密)和密钥
		// 创建初始化向量
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
		// cipher.init(Cipher.DECRYPT_MODE, keySpec);
		// 解密
		byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));

		return new String(bytes);
	}
}
