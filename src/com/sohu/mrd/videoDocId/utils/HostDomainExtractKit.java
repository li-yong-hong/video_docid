package com.sohu.mrd.videoDocId.utils;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import com.sohu.mrd.videoDocId.model.URLInfo;
/**
 * @author  Jin Guopan
 * @version 2016-12-8
 */
public class HostDomainExtractKit{
	private static Logger LOG = Logger.getLogger(HostDomainExtractKit.class);
	//一级域名提取
	private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";  
	// 一级域名提取  
	private static final String TOP_LEVEL_DOMAIN = "(\\w*\\.?){1}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";  
	// 二级域名提取  
	private static final String SECOND_LEVEL_DOMAIN="(\\w*\\.?){2}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";  
	// 三级域名提取  
	private static final String THREE_LEVEL_DOMAIN ="(\\w*\\.?){3}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";
	/**
	 * 取得顶级域名
	 * @param url
	 * @return
	 */
	public static String getTopLevelDomain(String url)
	{
		Pattern  pattern = Pattern.compile(RE_TOP , Pattern.CASE_INSENSITIVE);
		 String result = url;  
	        try {  
	            Matcher matcher = pattern.matcher(url);  
	            matcher.find();  
	            result = matcher.group();  
	        } catch (Exception e) {  
	        	LOG.error("取得顶级域名错误 "+e.getMessage());
	            e.printStackTrace();  
	        }  
	        return result;
	}
	/**
	 * 取得 二级域名
	 * @param url
	 * @return
	 */
	public static String getSecondLevelDomain(String url)
	{
		Pattern  pattern = Pattern.compile(SECOND_LEVEL_DOMAIN , Pattern.CASE_INSENSITIVE);
		 String result = url;  
	        try {  
	            Matcher matcher = pattern.matcher(url);  
	            matcher.find();  
	            result = matcher.group();  
	        } catch (Exception e) {
	        	LOG.error("取得二级域名错误 "+e.getMessage());
	            e.printStackTrace();  
	        }  
	        return result;
	}
	/**
	 * 取得三级域名
	 * @param url
	 * @return
	 */
	public static String getThreeLevelDomain(String url)
	{
		Pattern  pattern = Pattern.compile(THREE_LEVEL_DOMAIN , Pattern.CASE_INSENSITIVE);
		 String result = url;  
	        try {  
	            Matcher matcher = pattern.matcher(url);  
	            matcher.find();  
	            result = matcher.group();  
	        } catch (Exception e) {  
	            LOG.error("取得三级域名错误  "+e.getMessage());
	            e.printStackTrace();  
	        }  
	        return result;
	}
	public static String extractURLHost(String url)
	{
		String host ="";
		try {
			URL jURL = new URL(url);
			 host=jURL.getHost();
		} catch (Exception e){
			LOG.error("抽取host异常  "+ url+"\t"+e.getMessage());
			e.printStackTrace();
		}
		return host;
	}
    public static void main(String[] args){
        String url="http://cms.k.sohu.com/server/interface/readinfo.go?aid=1001&date=2013-07-25&ids=19293557_1&token=9e2e0350c8f8aa0db78ef6cbe70dc6fa";
        String url2="http://news.youth.cn/yl/201611/t20161116_8851373.htm";
        String url3="http://news.sina.com.cn/china/xlxw/2016-11-30/doc-ifxyawxa3196598.shtml";
        String url4="http://www.baidu.com";
        String urlHost= HostDomainExtractKit.getTopLevelDomain(url4);
       System.out.println("urlHost "+urlHost);
    } 
}
