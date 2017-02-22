package com.sohu.mrd.videoDocId.hbase;
import java.io.IOException;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Set;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.sohu.mrd.videoDocId.cach.localcach.CachData;
import com.sohu.mrd.videoDocId.cach.localcach.CachMap;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.utils.HBaseUitls;
/**
 * @author Jin Guopan
 * @version 2016-9-8
 */
public class QueryService {
	public static final Logger LOG=Logger.getLogger(QueryService.class);
	//默认一个对象缓存2分钟
	private static final long EXPIRATION_TIME = 2*60*1000;
	private QueryService() {
	}
	private static class sigleHolder {
		private static QueryService queryService = new QueryService();
	}
	public static QueryService getInstance() {
		return sigleHolder.queryService;
	}
	public String querySigleByGet(String tableName, String rowkey) {
		CachMap cachMap = CachMap.getInstance();
		String result = "";
		if (cachMap.get(rowkey) != null) {
			CachData cachData = cachMap.get(rowkey);
			if (System.currentTimeMillis() < cachData.getExpiration()) {
				return cachData.getValue();
			} else {
				// 如果过期进行查询数据库
				result = getHbase(tableName, rowkey);
				CachData putCachData = new CachData();
				putCachData.setRowkey(rowkey);
				putCachData.setValue(result);
				putCachData.setExpiration(System.currentTimeMillis()
						+ EXPIRATION_TIME);
				cachMap.put(rowkey, putCachData);
			}
		} else {
			result = getHbase(tableName, rowkey);
			CachData putCachData = new CachData();
			putCachData.setRowkey(rowkey);
			putCachData.setValue(result);
			putCachData.setExpiration(System.currentTimeMillis()
					+ EXPIRATION_TIME);
			cachMap.put(rowkey, putCachData);
		}
		return result;
	}

	public static  String getHbase(String tableName, String rowkey) {
		String  result = "";
		try {
			Get get = new Get(Bytes.toBytes(rowkey));
			HTable htable = new HTable(HBaseUitls.getConfig(), tableName);
			Result r = htable.get(get);
			byte[] value = r.getValue(Bytes
					.toBytes(HBaseConstant.COLUMN_FAMILY), Bytes
					.toBytes(HBaseConstant.QUALIFIER));
			result=Bytes.toString(value);
		} catch (Exception e) {
			LOG.error("查询数据异常"+e.getMessage());
		}
		return result;
	}
	/**
	 *qualifier 是随机的 
	 * @param tableName
	 * @param rowkey
	 * @return
	 */
	public static String getHBaseByRadomRowkey(String tableName,String rowkey)
	{
		String values = "";
		try {
			Get get = new Get(Bytes.toBytes(rowkey));
			HTable htable;
			htable = new HTable(HBaseUitls.getConfig(), tableName);
			Result r = htable.get(get);
			NavigableMap<byte[], byte[]>  map=r.getFamilyMap(Bytes.toBytes(HBaseConstant.COLUMN_FAMILY));
			if(map!=null)
			{
				Set<byte[]> keySet=map.keySet();
				Iterator<byte[]> it=keySet.iterator();
				StringBuilder valueSb = new StringBuilder();
				while(it.hasNext())
				{
					byte[] qualifier = it.next();
					String value=Bytes.toString(map.get(qualifier));
					valueSb.append(value);
					valueSb.append("\001");
				}
				values = valueSb.toString();
			}
		} catch (IOException e) {
			LOG.error("hbase 查询数据异常 "+ e.getMessage());
			e.printStackTrace();
		}
		
		return values;
	}
}
