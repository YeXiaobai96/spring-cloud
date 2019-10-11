package com.yofc.trace.util;

import java.security.MessageDigest;
import java.util.Random;


public class MyUtils {
	public static String getMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {

			e.printStackTrace();
			return "";
		}

		byte[] bytearr = inStr.getBytes();

		byte[] md5Bytes = md5.digest(bytearr);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	public static String getMD5More(String msg, Integer count) {
		String pwd = null;
		if (count < 1) {
			pwd = getMD5(msg);
		} else {
			pwd = getMD5(msg);
			for (int i = 1; i < count; i++) {
				pwd = getMD5(pwd);
			}
		}
		return pwd;
	}
	
	//加盐算法
	public static String getSalt(){
		Random r = new Random();
		StringBuffer sb = new StringBuffer(16);
 		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
 		int len = sb.length();
 		if (len < 16) {
 			for (int i = 0; i < 16 - len; i++) {
 				sb.append("0");
 			}
 		}
 		String salt = sb.toString();
 		return salt;
	}
	
	
	public static String getPwd(String pwd){
		
		return getMD5More(pwd,2);
	}
	public static void main(String[] args) {
		System.out.println(getMD5("哈d1"));
	}

}
