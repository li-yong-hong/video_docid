package com.sohu.mrd.videoDocId.utils;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * @author  Jin Guopan
 * @version 2016-11-22
 */
public class MD5Utils {
	public static void main(String[] args) {
		String md5=getMD5("123123123123");
		System.out.println("md5 "+md5);
	}
	public static String getMD5(String str) {
		String md5=DigestUtils.md5Hex(str);
		StringBuilder sb=new StringBuilder(md5);
		sb.insert(16,"-");
		return sb.toString();
	}
}
