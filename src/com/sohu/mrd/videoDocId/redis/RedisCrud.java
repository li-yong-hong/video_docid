package com.sohu.mrd.videoDocId.redis;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.sohu.mrd.framework.redis.client.client.CodisLocalClient;
/**
 * @author Jin Guopan
   @creation 2016年12月22日
   redis相关的操作 list 和 zset的使用。
 */
public class RedisCrud {
	private static CodisLocalClient codisLocalClient=RedisConnection.codisLocalClient;
	/**
	 * value 是个 string 类型
	 * @param key
	 * @param value
	 */
	public static  void set(String key,String value)
	{
		codisLocalClient.set(key, value);
	}
	
	public static String  get(String key)
	{
		return codisLocalClient.get(key);
	}
	
	/**
	 * value 是个 list
	 * @param key
	 * @param value
	 */
	public static void set2list(String key,String value)
	{
		codisLocalClient.lpush(key, value);
	}
	
	public static List<String> getList(String key)
	{
		List<String>  list=codisLocalClient.lrange(key, 0, -1);
		return list;
	}
	/**
	 * value 是个 set 集合 
	 * @param key
	 * @param value
	 */
	public static void put2set(String key,String value)
	{
		codisLocalClient.sadd(key, value);
	}
	
	public static Set<String>  getSet(String key)
	{
		return codisLocalClient.smembers(key);
	}
	/**
	 * 批量删除keys
	 * @param pattern
	 */
	public static void  deleteKeys(String pattern)
	{
		Set<String> keys=codisLocalClient.keys(pattern);
		if(keys==null || keys.size()<=0)
		{
			return;
		}
		String[] keystrs=(String[]) keys.toArray();
		codisLocalClient.del(keystrs);
	}
	
	/**
	 * 删除单个key
	 */
	public static void main(String[] args){
//		RedisCrud.set2list("test_table_url_index_10001", "123");
//		RedisCrud.set2list("test_table_url_index_10001", "456");
//		RedisCrud.set2list("test_table_url_index_10001", "789");
//		RedisCrud.set2list("test_table_url_index_10002", "123");
//		RedisCrud.set2list("test_table_url_index_10002", "456");
//		RedisCrud.set2list("test_table_url_index_10002", "789");
//		List<String>  list1=RedisCrud.getList("test_table_url_index_10001");
//		List<String>  list2=RedisCrud.getList("test_table_url_index_10002");
//		List<String>  list3=RedisCrud.getList("test_table_url_index_10003");
//		for(int i=0;i<list1.size();i++)
//		{
//			System.out.println(list1.get(i));
//		}
//		System.out.println(list1.toString());
//		System.out.println(list1.size());
//		System.out.println(list2.size());
//		System.out.println(list3.size());
		//redis 是 key value　结构　　list 和  set 是相对value而言。
//		RedisCrud.set2list("test_table_url_index_100014", "123");
//		RedisCrud.set2list("test_table_url_index_100014", "123");
//		RedisCrud.set2list("test_table_url_index_100014", "456");
//		RedisCrud.set2list("test_table_url_index_100014", "456");
//		List<String> list= RedisCrud.getList("test_table_url_index_100014");
//		System.out.println("list"+list.toString());
//		
//		
		RedisCrud.put2set("test_table_url_index_100013", "123");
		RedisCrud.put2set("test_table_url_index_100013", "123");
		RedisCrud.put2set("test_table_url_index_100013", "456");
		RedisCrud.put2set("test_table_url_index_100013", "456");
//		Set<String>  set =RedisCrud.getSet("test_table_url_index_100013");
//		System.out.println("set "+set.toString());
//		
//		String result=codisLocalClient.get("test_table_url_index_100013"); //不存在返回的是 空对象
//		if(result==null)
//		{
//			System.out.println("不存在");
//		}else{
//			System.out.println("存在");
//		}
		//RedisCrud.deleteKeys("test_table_url_index_1000*");
//		List<String>   result=RedisCrud.getList("test_table_url_index_100014");
		Set<String>  contentSet=codisLocalClient.keys("video_content_index_table*");
		if(contentSet!=null)
		{
			System.out.println(contentSet.size());
		}
		Set<String> urlSet=codisLocalClient.keys("video_url_index_table*");
		if(urlSet!=null)
		{
			System.out.println("urlSet "+urlSet);
		}
		
		Set<String> testUrlSet=codisLocalClient.keys("test_table_url_index_100013");
		System.out.println("testUrlSet "+testUrlSet.size());
	}
}
