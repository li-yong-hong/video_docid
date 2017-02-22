package com.sohu.mrd.videoDocId.cach.localcach;
import java.util.LinkedHashMap;
/**
 * @author Jin Guopan
 * @version 2016-9-7
 */
public class CachMap extends
		LinkedHashMap<String, CachData> {
	// 默认存2000个 
	private final static int DEFAULT_SIZE = 2000;
	// 初始容量
	private CachMap(int max_size) {
		// 初始容量是30 当个数为30*0.75的时候容量倍，如果为true 表示按照访问顺序放置，
		// 最近访问的放在前面
		super(30, 0.75f, true);
	}
	private static class sigleHolder {
		private static CachMap cachMap = new CachMap(
				DEFAULT_SIZE);
	}
	/**
	 * 单例模式向外界提供类
	 */
	public static CachMap getInstance()
	{
		return sigleHolder.cachMap;
	}
	/**
	 * 当容量超过最大容量 返回ture进行删除
	 */
	@Override
	protected boolean removeEldestEntry(
			java.util.Map.Entry<String, CachData> eldest) {
		return size() > DEFAULT_SIZE;
	}

}
