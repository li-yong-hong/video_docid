package com.sohu.mrd.videoDocId.hbase;

import java.util.UUID;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.utils.HBaseUitls;
/**
 * @author Jin Guopan
 * @version 2016-9-1
 */
public class PutData {
	public static final Logger logger=Logger.getLogger(PutData.class);
	/**
	 * 根据rowkey
	 */
	public static void putData(String tableName, String rowkey, String family,
			String qualifier, String value) {
		try {
			HTable table = new HTable(HBaseUitls.getConfig(), tableName);
			Put put = new Put(rowkey.getBytes());
			put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());
			table.put(put);
		} catch (Exception e) {
			logger.error("插入数据异常 "+tableName+"\t"+rowkey+"\t"+value+"\t"+e.getMessage());
		}
	}
	public static void main(String[] args){
		
		HBaseUitls.autoCreateTable("test_test_IIIII");
		String values=QueryService.getHBaseByRadomRowkey("test_test_IIIII", "123");
		System.out.println("values "+values);
		
		//putData("test_test_IIIII", "456", "cf", "789", "888");
	}
}
