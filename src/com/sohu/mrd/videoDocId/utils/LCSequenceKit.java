package com.sohu.mrd.videoDocId.utils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * @author  Jin Guopan
 * @version 2016-9-1
 */
public class LCSequenceKit {
	private static Logger LOG=Logger.getLogger(LCSequenceKit.class);
	public static void main(String[] args) {
		List<String>  list1=new ArrayList<String>();
		List<String>  list2=new ArrayList<String>();
		list1.add("我在");
		list1.add("吃饭");
		list2.add("我在");
		list2.add("吃西瓜");
		int sum1=getSequencelength(list1,list2);
		int sum2=getSequencelength("我在吃饭","我在吃西瓜");
		System.out.println("sum1 "+sum1+";sum2 "+sum2);
	}
	/**
	 * 两个集合最长公共子序列
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int getSequencelength(List<String> list1,List<String> list2)
	{
		StringBuilder sb1=new StringBuilder();
		StringBuilder sb2=new StringBuilder();
		for(int i=0;i<list1.size();i++)
		{
			sb1.append(list1.get(i));
		}
		for(int j=0;j<list2.size();j++)
		{
			sb2.append(list2.get(j));
		}
		String s1=sb1.toString();
		String s2=sb2.toString();
		sb1.delete(0, sb1.length());
		sb2.delete(0, sb2.length());
		return getSequencelength(s1,s2);
	}
	
	public static int  getSequencelength(String s1,String s2)
	{
		String commonString=lcse(s1,s2);
		return commonString.length();
	}
	
	private  static String lcse(String str1, String str2) {
		if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
			return "";
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		int[][] dp = getdp(chs1, chs2);
		int m = chs1.length - 1;
		int n = chs2.length - 1;
		char[] res = new char[dp[m][n]];
		int index = res.length - 1;
		while (index >= 0) {
			if (n > 0 && dp[m][n] == dp[m][n - 1]) {
				n--;
			} else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
				m--;
			} else {
				res[index--] = chs1[m];
				m--;
				n--;
			}
		}
		return String.valueOf(res);
	}
	private  static int[][] getdp(char[] str1, char[] str2){
		int[][] dp = new int[str1.length][str2.length];
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		for (int i = 1; i < str1.length; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
		}
		for (int j = 1; j < str2.length; j++) {
			dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp;
	}
}
