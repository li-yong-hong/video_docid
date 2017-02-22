package com.sohu.mrd.videoDocId.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class KillTag {
public static String killTags(String news) {
		if(news==null){
			return "";
		}
		String s = news.replaceAll("amp;", "");
		if(s==null){
			return "";
		}	
		s =s.replaceAll("&lt;", "<");
		s=s.replaceAll("</p>", "。");
		if(s==null){
			return "";
		}
		s =s.replaceAll("&gt;", ">");
		if(s==null){
			return "";
		}

		/*
		 * 过滤CSS样式<u></u>
		 */
		Pattern pattern = Pattern.compile("<(span)?(\\s)*style.*?style>|<(span)?(\\s)*style=.*?>",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(s);
		String str = matcher.replaceAll("");

		
		/*
		 * 过滤HTML标签
		 */
		Pattern pattern2 = Pattern.compile("(<[^>]+>)", Pattern.DOTALL);
		Matcher matcher2 = pattern2.matcher(str);
		String strhttp = matcher2.replaceAll("");
		/*
		 * 过滤URL网址
		 */
		
		String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|／／)(\\s)*)?"
		+ "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
		+ "("
		+ "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
		+ "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
		+ "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
		+ "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
		+ "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
		+ ")"
		+ "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
		+ "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";

		Pattern p1 = Pattern.compile(regEx, Pattern.DOTALL);
		
		
		String[] subs = strhttp.split(" ");
		StringBuffer  buf = new StringBuffer();
		for(String strElement:subs){
			Matcher matchhttp = p1.matcher(strElement);
			String temp = matchhttp.replaceAll("");
			buf.append(temp);
			buf.append(" ");
		}
		String strnew  = buf.toString().replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");
		/*
		 * 过滤标点符号
		 */
//		Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
//		Matcher matchercomma = patterncomma.matcher(strnew);
//		String strout = matchercomma.replaceAll(" ").replaceAll("\\pP", " ");
//		return strout;
		return strnew;
	}

}
