package com.sohu.mrd.videoDocId.utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * map根据value进行自定义排序
 * @author jinguopan
 */
public class MapKit {
	public static final Log LOG=LogFactory.getLog(MapKit.class);
	/**
	 * 根据value对map进行排序
	 * @param map
	 * @return
	 */
	public static Map<String,Integer>  sortMapByValue(Map<String,Integer> map)
	{
		if(null==map)
		{
			LOG.info("map 为 空");
			System.exit(-1);
		}
		List<Map.Entry<String,Integer>> list=new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list, new CustomerComparator());
		
		Map<String, Integer>  linkedHashMap=new LinkedHashMap<String,Integer>(); 
		
		for(int i=0;i<list.size();i++)
		{
			linkedHashMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		
		return linkedHashMap;
	}
	
	static class CustomerComparator implements Comparator<Map.Entry<String,Integer>>{
		public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
			return -1*entry1.getValue().compareTo(entry2.getValue());
		}
		
	}
	static class CustomerDoubleComparator implements Comparator<Map.Entry<String,Double>>{
		public int compare(Entry<String, Double> entry1, Entry<String, Double> entry2) {
			return -1*entry1.getValue().compareTo(entry2.getValue());
		}
		
	}
	public static Map<String,Double>  sortMapByDoubleValue(Map<String,Double> map)
	{
		if(null==map)
		{
			LOG.info("map 为 空");
			System.exit(-1);
		}
		List<Map.Entry<String,Double>> list=new ArrayList<Map.Entry<String,Double>>(map.entrySet());
		Collections.sort(list, new CustomerDoubleComparator());
		
		Map<String, Double>  linkedHashMap=new LinkedHashMap<String,Double>(); 
		
		for(int i=0;i<list.size();i++)
		{
			linkedHashMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		
		return linkedHashMap;
	}
	/**
	 * 对list中的单词进行count且排序
	 * @param words
	 * @return
	 */
	public static Map<String,Integer>  wordCountsort(List<String> words)
	{
		Map<String,Integer>  map=new HashMap<String,Integer> ();
		for(int i=0;i<words.size();i++)
		{
			if(map.get(words.get(i))!=null && !words.get(i).trim().equals("")) //对于空字符串，不计算在内
			{
				map.put(words.get(i),map.get(words.get(i))+1);
			}else{
				if(!words.get(i).trim().equals(""))
				{
					map.put(words.get(i), 1);
				}
			}
		}
		Map<String, Integer>  resultMap=sortMapByValue(map);
	    return resultMap;
	}
	
	public static <K, V> void printMap2Console(Map<String,Integer> map)
	{
		Set<String> keySet=map.keySet();
		Iterator<String>  it=keySet.iterator();
		while(it.hasNext())
		{
			String key=it.next();
			Integer value=map.get(key);
			System.out.println(key+":"+value);
		}
	}
	public static <K, V> void printMap2ConsoleDouble(Map<String,Double> map)
	{
		Set<String> keySet=map.keySet();
		Iterator<String>  it=keySet.iterator();
		while(it.hasNext())
		{
			String key=it.next();
			Double value=map.get(key);
			System.out.println(key+":"+value);
		}
	}
	public static void writeMap2File(Map<String,Integer> map,String filePath)
	{
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(filePath);
			Set<String> keySet=map.keySet();
			Iterator<String>  it=keySet.iterator();
			StringBuilder sb=new StringBuilder();
			while(it.hasNext())
			{
				String key=it.next();
				Integer value=map.get(key);
				sb.append(key);
				sb.append(":");
				sb.append(value);
				sb.append("\n");
				fos.write(sb.toString().getBytes());
				sb.delete(0, sb.length());
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}finally{
			  try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		List<String> words=new ArrayList<String>();
		words.add("我");
		words.add("我");
		words.add("我");
		words.add("我");
		words.add("我");
		words.add("你");
		words.add("你");
		words.add("你");
		printMap2Console(wordCountsort(words));
	}
}
