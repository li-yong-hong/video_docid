package com.sohu.mrd.videoDocId.cach.localcach;
/**
 * @author  Jin Guopan
 * @version 2016-9-7
 */
public class CachData {
	private String rowkey;
	private String value;
	private long expiration;
	public String getRowkey() {
		return rowkey;
	}
	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getExpiration() {
		return expiration;
	}
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	
}
