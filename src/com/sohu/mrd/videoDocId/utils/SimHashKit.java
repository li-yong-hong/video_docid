package com.sohu.mrd.videoDocId.utils;

import java.math.BigInteger;

/**
 * @author  Jin Guopan
 * @version 2016-12-15
 */
public class SimHashKit {
	/**
	 * java 中不能直接表示二进制数，只能用字符串表示
	 * n&(n-1) 可以清除n最右边的1 比如，7(0111) = 6(0110) + 1(0001) n 为 n -1 +1  因为与操作都是1位1，不是都为0
	 * 7(0111)&6(0110)= 0110  消除最右边的 1，
	 * @return
	 */
	public static int  getHanMingDistance(String  simhash1,String simhash2)
	{
		//把64 位 的二进制字符串转换为 bigInteger 类型
		BigInteger simhashBigInt1 = new BigInteger(simhash1, 2);
		BigInteger simhashBigInt2 = new BigInteger(simhash2, 2);
		int distance =0;
		BigInteger xorHash=simhashBigInt1.xor(simhashBigInt2);
		while(!xorHash.equals(BigInteger.ZERO))
		{
			xorHash=xorHash.and(xorHash.subtract(BigInteger.ONE));
			distance++;
		}
		return distance;
	}

}
