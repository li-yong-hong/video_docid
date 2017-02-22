package com.sohu.mrd.videoDocId.utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
   @author Jin Guopan
   @creation 2016年12月8日
      两个集合的并集和交集
 */
public class SetKit{
	/**
	 * 交个集合的交集
	 * @return
	 */
	public static int  getListIntersection(List<String> list1,List<String> list2)
	{
		int intersectionNumber=0;
		if(list1==null || list2 ==null)
		{
			return intersectionNumber;
		}else {
			list1.retainAll(list2);
			return list1.size();
		}
	}
	/**
	 * 两个集合的并集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static int  getListUnion(List<String> list1,List<String> list2)
	{
		int unionNumber = 0;
		if(list1 == null && list2!= null)
		{
			unionNumber=list2.size();
		}
		if(list1 != null && list2 == null)
		{
			unionNumber = list1.size();
		}
		if(list1 != null && list2!=null)
		{
			HashSet<String>  set = new HashSet<String>();
			set.addAll(list1);
			set.addAll(list2);
			unionNumber = set.size();
		}
		return unionNumber;
	}
	/**
	 * 利用 set 集合的唯一性返回两个字符串的并集
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String>  getListUnion(String sentence1, String sentence2)
	{
		HashSet<String> set=new  HashSet<String>();
		char[] charArray1=sentence1.toCharArray();
		char[] charArray2=sentence2.toCharArray();
		for(int i=0;i<charArray1.length;i++)
		{
			set.add(String.valueOf(charArray1[i]));
		}
		for(int j=0;j<charArray2.length;j++)
		{
			set.add(String.valueOf(charArray2[j]));
		}
		List<String> list=new ArrayList<String>(set);
		return list;
	}
	public static void main(String[] args) {
		List<String>  list1 = new ArrayList<String>();
		List<String>  list2= new ArrayList<String>();
		list1.add("1");
		list1.add("1");
		list1.add("1");
		
		list2.add("1");
		list2.add("2");
		
		
		int intersection=SetKit.getListIntersection(list1, list2);
		int union=SetKit.getListUnion(list1, list2);
		System.out.println("intersection "+intersection);
		System.out.println("union "+union);
		
	}
}
