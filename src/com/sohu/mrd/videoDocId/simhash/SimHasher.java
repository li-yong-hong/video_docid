package com.sohu.mrd.videoDocId.simhash;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * 文本去重算法的simhash类
 * 步骤如下：
 * 1，对文本分词，得到N维特征向量（默认为64维）
 * 2，为分词设置权重（tf-idf）
 * 3，为特征向量计算哈希
 * 4，对所有特征向量加权，累加（目前仅进行非加权累加）
 * 5，对累加结果，大于零置一，小于零置零
 * 6，得到文本指纹（fingerprint）
 * @author Jin Guopan
 */
public class SimHasher {
	private static final Logger LOG = Logger.getLogger(SimHasher.class);
	private String hash;
	private BigInteger signature;
	private KeywordExtractor wordExtractor = KeywordExtractor.getInstance();
	/**
	 * 构造函数
	 * 
	 * @param content 字符串
	 */
	public SimHasher(String content) {
		this.analysis(content);
	}
	private void analysis(String content){
		Map<String, Double> wordInfos = wordExtractor.extract(content);
		double[] featureVector = new double[FNVHash.HASH_BITS];
		Set<String> words = wordInfos.keySet();
		for (String word : words) {
			BigInteger wordhash = FNVHash.fnv1aHash64(word);
			for (int i = 0; i < FNVHash.HASH_BITS; i++) {
				BigInteger bitmask = BigInteger.ONE.shiftLeft(FNVHash.HASH_BITS - i - 1);
				if (wordhash.and(bitmask).signum() != 0) {
					featureVector[i] += wordInfos.get(word);
				} else {
					featureVector[i] -= wordInfos.get(word);
				}
			}
		}
		BigInteger signature = BigInteger.ZERO;
		StringBuffer hashBuffer = new StringBuffer();
		for (int i = 0; i < FNVHash.HASH_BITS; i++) {
			if (featureVector[i] >= 0){
				signature = signature.add(BigInteger.ONE.shiftLeft(FNVHash.HASH_BITS - i - 1));
				hashBuffer.append("1");
			} else {
				hashBuffer.append("0");
			}
		}
		this.hash = hashBuffer.toString();
		this.signature = signature;
	}
	/**
	 * 汉明距离
	 * 
	 * @param targetSignature 比较签名
	 * @return
	 */
	public int getHammingDistance(BigInteger targetSignature) {
		BigInteger x = this.getSignature().xor(targetSignature);
		int tot = 0;

		// 统计x中二进制位数为1的个数
		// 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，
		// 对吧，然后，n&(n-1)就相当于把后面的数字清0，
		// 我们看n能做多少次这样的操作就OK了。

		while (x.signum() != 0) {
			tot += 1;
			x = x.and(x.subtract(new BigInteger("1")));
		}
		return tot;
	}
	/**
	 * hash距离。二进制比较
	 * @param targetHash 比较目标
	 * @return
	 */
	public int getHashDistance(String targetHash) {
		int distance;
		if (this.getHash().length() != targetHash.length()) {
			distance = -1;
		} else {
			distance = 0;
			for (int i = 0; i < this.getHash().length(); i++) {
				if (this.getHash().charAt(i) != targetHash.charAt(i)) {
					distance++;
				}
			}
		}
		return distance;
	}
	public String getHash() {
		return this.hash;
	}
	public BigInteger getSignature(){
		return this.signature;
	}
	public static void main(String[] args){
		SimHasher simHasher = new SimHasher("你妈妈喊你回家吃饭");
		String simHash = simHasher.getHash();
		System.out.println("生成的simHash为 "+simHash);
		//System.out.println("simHash.length() "+simHash.length());
		String simHashFragment1=simHash.substring(0, 16); //包括第0个不包括第16个
		System.out.println("simHashFragment1 "+simHashFragment1);
		String simHashFragment2=simHash.substring(16, 32);
		System.out.println("simHashFragment2 "+simHashFragment2);
		String simHashFragment3 = simHash.substring(32, 48);
		System.out.println("simHashFragment3 "+simHashFragment3);
		String simHashFragment4 = simHash.substring(48, 64);
		System.out.println("simHashFragment4 "+simHashFragment4);
//		System.out.println("simHasher.getHash() "+simHasher.getHash());
//		SimHasher simHasher2 = new SimHasher("天安门妈妈喊你回家吃饭。");
//		System.out.println("simHasher2.getHash()"+simHasher2.getHash());
//		int distance=simHasher.getHashDistance(simHasher2.getHash());
//		System.out.println("两者之间的汉明距离是  "+distance);
	}
}
