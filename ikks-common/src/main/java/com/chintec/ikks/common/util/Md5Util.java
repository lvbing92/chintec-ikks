package com.chintec.ikks.common.util;

import java.security.MessageDigest;
import java.util.Base64;


/**
 * @author rubin
 */
public class Md5Util {
	
	public static String  encode(String msg){
		try {
			MessageDigest messageDigest=MessageDigest.getInstance("md5");
			return Base64.getEncoder().encodeToString(messageDigest.digest(msg.getBytes())) ;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		boolean flag;
		flag = 1==1;
		flag &= 1==1;
		flag &= 2==2;
		System.out.println(flag);

	}
}
