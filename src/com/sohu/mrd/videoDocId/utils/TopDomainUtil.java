package com.sohu.mrd.videoDocId.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TopDomainUtil{  
    private Pattern pattern;  
    // 定义正则表达式，域名的根需要自定义，这里不全  
    private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";  
    // 构造函数  
    public TopDomainUtil() {  
        pattern = Pattern.compile(RE_TOP , Pattern.CASE_INSENSITIVE);  
    }  
    public String getTopDomain(String url) {  
        String result = url;  
        try {  
            Matcher matcher = this.pattern.matcher(url);  
            matcher.find();  
            result = matcher.group();  
        } catch (Exception e) {  
            System.out.println("[getTopDomain ERROR]====>");  
            e.printStackTrace();  
        }  
        return result;  
    }  
    public static void main(String[] args){
        TopDomainUtil obj = new TopDomainUtil();  
        String url="http://cms.k.sohu.com/server/interface/readinfo.go?aid=1001&date=2013-07-25&ids=19293557_1&token=9e2e0350c8f8aa0db78ef6cbe70dc6fa";
        String url2="http://news.youth.cn/yl/201611/t20161116_8851373.htm";
        String url3="http://news.sina.com.cn/china/xlxw/2016-11-30/doc-ifxyawxa3196598.shtml";
        String url4="http://www.baidu.com";
        String topDomain=obj.getTopDomain(url);
        System.out.println("topDomain "+topDomain);
        String topDomain1=obj.getTopDomain(url2);
        System.out.println("topDomain1 "+topDomain1);
        String topDomain2=obj.getTopDomain(url3);
        System.out.println("topDomain2 "+topDomain2);
        String topDomain4=obj.getTopDomain(url4);
        System.out.println("topDomain4 "+topDomain4); 
    }   
  
}  