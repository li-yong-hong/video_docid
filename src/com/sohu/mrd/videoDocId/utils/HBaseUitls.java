package com.sohu.mrd.videoDocId.utils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.log4j.Logger;

import com.sohu.mrd.videoDocId.constant.HBaseConstant;
/**
 * @author  Jin Guopan
 * @version 2016-9-7
 */
public class HBaseUitls {
	public static final Logger LOG=Logger.getLogger(HBaseUitls.class);
	private static Configuration  config;
	static{
		try {
			config=HBaseConfiguration.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private  static class instanceHolder{
		private static HConnection connection;
		private static int  tryTime=3;//重试三次连接
		private static boolean  flag=true;//需要重试
		static{
			try {
				while(tryTime-->0&&flag )
				connection=HConnectionManager.getConnection(config);
				flag=false;
			} catch (Exception e) {
				e.printStackTrace();
				flag=true;
			}
			
		}
	}
	/**
	 * 返回连接的实例
	 */
	
	public static HConnection getConnection()
	{
		return instanceHolder.connection;
	}
	/**
	 * 返回hbase环境变量
	 * @return
	 */
	public static Configuration getConfig()
	{
		return config;
	}
	//自动创建hbase表
	public static void autoCreateTable(String tableName){
		try {
			HBaseAdmin hbaseAdmin = new HBaseAdmin(HBaseUitls.getConfig());
			if (!hbaseAdmin.tableExists(tableName)) {
				HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
				HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(
						HBaseConstant.COLUMN_FAMILY);
				hTableDescriptor.addFamily(hColumnDescriptor);
				hbaseAdmin.createTable(hTableDescriptor);
			}
		} catch (Exception e) {
			LOG.error("创建表异常 "+tableName+"\t"+e.getMessage());
		}
	}
	
	
}
