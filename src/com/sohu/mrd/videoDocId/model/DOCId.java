package com.sohu.mrd.videoDocId.model;
/**
 * @author  Jin Guopan
 * @version 2016-11-22
 * docId的组成 
 */
public class DOCId {
	private String flagPosition ; //预留位置
	private String hostPosition;  //主机位置
	private String urlPosition;  //url位置
	private String otherPositon;// 除去host位置部分
	public String getFlagPosition() {
		return flagPosition;
	}
	public void setFlagPosition(String flagPosition) {
		this.flagPosition = flagPosition;
	}
	public String getHostPosition() {
		return hostPosition;
	}
	public void setHostPosition(String hostPosition) {
		this.hostPosition = hostPosition;
	}
	public String getUrlPosition() {
		return urlPosition;
	}
	public void setUrlPosition(String urlPosition) {
		this.urlPosition = urlPosition;
	}
	public String getOtherPositon() {
		return otherPositon;
	}
	public void setOtherPositon(String otherPositon) {
		this.otherPositon = otherPositon;
	}
}
