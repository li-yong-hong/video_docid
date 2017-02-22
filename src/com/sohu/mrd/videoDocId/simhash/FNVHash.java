package com.sohu.mrd.videoDocId.simhash;
import java.math.BigInteger;
/**
 * FNVhash
 * @author Jin Guopan
 */
public final class FNVHash {

	public static final int HASH_BITS = 64;
	public static final BigInteger FNV_64_INIT = new BigInteger("14695981039346656037");
	public static final BigInteger FNV_64_PRIME = new BigInteger("1099511628211");
	public static final BigInteger MASK_64 = BigInteger.ONE.shiftLeft(HASH_BITS).subtract(BigInteger.ONE);
	public static void main(String[] args) {
	}
	private FNVHash() {
	}
	/**
	 * fnv-1a hash算法，将字符串转换为64位hash值
	 * 
	 * @param str str
	 * @return
	 */
	public static BigInteger fnv1aHash64(String str) {
		BigInteger hash = FNV_64_INIT; //14695981039346656037
		int len = str.length();
		for (int i = 0; i < len; i++) {
			
			hash = hash.xor(BigInteger.valueOf(str.charAt(i)));
			hash = hash.multiply(FNV_64_PRIME);// FNV_64_PRIME=1099511628211
		}
		hash = hash.and(MASK_64);
		return hash;
	}
	/**
	 * 返回二进制串hash距离
	 * 
	 * @param str1 str1
	 * @param str2 str2
	 * @return
	 */
	public static int getDistance(String str1, String str2) {
		int distance;
		if (str1.length() != str2.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < str1.length(); i++) {
				if (str1.charAt(i) != str2.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}
}