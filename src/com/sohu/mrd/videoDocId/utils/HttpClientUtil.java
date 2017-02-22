package com.sohu.mrd.videoDocId.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {  
    public static String doPost(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = HttpClients.createDefault();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
    public static  String executeGet(String url){  
        BufferedReader in = null;  
        String content = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();  
            // 实例化HTTP方法  
            HttpGet request = new HttpGet();  
            request.setURI(new java.net.URI(url));
            HttpResponse response = client.execute(request);  
  
            in = new BufferedReader(new InputStreamReader(response.getEntity()  
                    .getContent()));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";  
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            content = sb.toString();  
        } finally {  
            if (in != null) {  
                try {  
                    in.close();// 最后要关闭BufferedReader  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            return content;  
        }  
    } 
} 