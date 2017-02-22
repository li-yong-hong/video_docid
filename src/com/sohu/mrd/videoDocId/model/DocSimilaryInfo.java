package com.sohu.mrd.videoDocId.model;
/**
 * @author  Jin Guopan
 * @version 2016-12-15
 */
public class DocSimilaryInfo{
	private String  title;
	private String content;
	private String docId;
	private int distance;
	private double titleSimilary;
	private double  contentSimilary;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public double getTitleSimilary() {
		return titleSimilary;
	}
	public void setTitleSimilary(double titleSimilary) {
		this.titleSimilary = titleSimilary;
	}
	public double getContentSimilary() {
		return contentSimilary;
	}
	public void setContentSimilary(double contentSimilary) {
		this.contentSimilary = contentSimilary;
	}
	@Override
	public String toString() {
		return "DocSimilaryInfo [content=" + content + ", contentSimilary="
				+ contentSimilary + ", distance=" + distance + ", docId="
				+ docId + ", title=" + title + ", titleSimilary="
				+ titleSimilary + "]";
	}
	
	
}
