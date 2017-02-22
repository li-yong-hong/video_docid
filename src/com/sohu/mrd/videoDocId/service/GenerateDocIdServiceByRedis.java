package com.sohu.mrd.videoDocId.service;
import java.net.URL;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.videoDocId.constant.Constant;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.constant.RedisConstant;
import com.sohu.mrd.videoDocId.duplicate.TitleContentDuplicate;
import com.sohu.mrd.videoDocId.duplicate.TitleContentDuplicateByRedis;
import com.sohu.mrd.videoDocId.duplicate.URLDuplicate;
import com.sohu.mrd.videoDocId.duplicate.URLDuplicateByRedis;
import com.sohu.mrd.videoDocId.hbase.PutData;
import com.sohu.mrd.videoDocId.model.URLInfo;
import com.sohu.mrd.videoDocId.redis.RedisCrud;
import com.sohu.mrd.videoDocId.simhash.SimHasher;
import com.sohu.mrd.videoDocId.utils.HBaseUitls;
import com.sohu.mrd.videoDocId.utils.HostDomainExtractKit;
import com.sohu.mrd.videoDocId.utils.KillPuctuation;
import com.sohu.mrd.videoDocId.utils.KillTag;
import com.sohu.mrd.videoDocId.utils.MD5Kit;
import com.sohu.mrd.videoDocId.utils.MD5Utils;
/**
 * @author Jin Guopan
 * @version 2016-11-22
 */
public class GenerateDocIdServiceByRedis {
	private  static final Logger LOG = Logger.getLogger(GenerateDocIdServiceByRedis.class);
	private  GenerateDocIdServiceByRedis(){}
	private static  class sigletonHolder{
		private static  GenerateDocIdServiceByRedis  instance = new GenerateDocIdServiceByRedis();
	}
	public static  GenerateDocIdServiceByRedis  getInstance()
	{
		return sigletonHolder.instance;
	}
	public String getDocId(String url, String title,String content){
		String docId ="";
		//首先通过url进行去重判断
		String flag=URLDuplicateByRedis.duplicatebyURL(url);
		if(flag.equals(Constant.DUPLICATE_FLAG)) //没有重复的
		{
			//根据文章标题和内容进行排重
			TitleContentDuplicateByRedis titleContentDuplicateByRedis =new TitleContentDuplicateByRedis();
			//对title 和 content 去除标签
			String clearTitle = KillPuctuation.killPuctuation(KillTag.killTags(title)) ;
			String clearContent = KillPuctuation.killPuctuation(KillTag.killTags(content)) ;
			SimHasher simHasher = new SimHasher(clearTitle+"\t"+clearContent);
			String simHash =simHasher.getHash();
			String contentFlag=titleContentDuplicateByRedis.duplicateByTitleContent(simHash, clearTitle, clearContent);
			if(contentFlag.equals(Constant.DUPLICATE_FLAG))//没有重复的
			{
			   //产生新的docId	
				String newDocId=generateDocId(url);
				//建立url排重索引
				buildURLIndex(url,  newDocId);
				//建立标题内容排重索引
				buildTitleContentIndex(simHash, newDocId,  clearTitle, clearContent);
				docId = newDocId;
				return docId;
			}else{
				// url 索引库没有，而通过Jaccard 相似度 打成同一个 docId的，建立 url 索引，防止相同的url进来再次进行jaccard相似度计算
				docId=contentFlag;
				LOG.info("开始建立二次索引 "+url);
				buildURLIndex(url,  docId);
				LOG.info("通过content进行排重的 url "+url);
				return docId;
			}
		}else{
			docId=flag;
			return docId;
		}
	}
	private  String generateDocId(String url){
		URLInfo urlInfo = extractURLHost(url);
		String host = urlInfo.getHost();
		String domain = urlInfo.getDomain();
		MD5Kit md5Kit = new MD5Kit();
		String urlMD5=md5Kit.md5(url);
		String hostMd5=md5Kit.md5(host);
		String domainMd5=md5Kit.md5(domain);
		String md5 = md5Kit.md5("video"+urlMD5+hostMd5+domainMd5);
		StringBuilder docIdSB=new StringBuilder(md5);
		String docId=docIdSB.insert(16, "-").toString();
		return docId;
	}
	/**
	 * 创建文章内容和标题的索引
	 * TODO 当 key 相同的时候，覆盖value的问题
	 */
	private  void buildTitleContentIndex(String simHash, String docId, String title,String content) {
		long storageTime = System.currentTimeMillis();
		if (simHash.length() >= 64) {
			String simHashFragment1 = simHash.substring(0, 16);
			String simHashFragment2 = simHash.substring(16, 32);
			String simHashFragment3 = simHash.substring(32, 48);
			String simHashFragment4 = simHash.substring(48, 64);
			// 以四个分段为索引分别存放simHash
			String value1 = title+"#&"+ content+"#&"+docId+"#&"+simHash+"#&"+storageTime;
			String value2 = title+"#&"+ content+"#&"+docId+"#&"+simHash+"#&"+storageTime;
			String value3 = title+"#&"+ content+"#&"+docId+"#&"+simHash+"#&"+storageTime;
			String value4 = title+"#&"+ content+"#&"+docId+"#&"+simHash+"#&"+storageTime;
			String redisKey1=RedisConstant.KEY_PREFIX_VIDEO_CONTENT_INDEX_TABLE+simHashFragment1;
			String redisKey2=RedisConstant.KEY_PREFIX_VIDEO_CONTENT_INDEX_TABLE+simHashFragment2;
			String redisKey3=RedisConstant.KEY_PREFIX_VIDEO_CONTENT_INDEX_TABLE+simHashFragment3;
			String redisKey4=RedisConstant.KEY_PREFIX_VIDEO_CONTENT_INDEX_TABLE+simHashFragment4;
			RedisCrud.set2list(redisKey1, value1);
			RedisCrud.set2list(redisKey2, value2);
			RedisCrud.set2list(redisKey3, value3);
			RedisCrud.set2list(redisKey4, value4);
		} else {
			LOG.error("simHash长度错误 ，长度为  " + simHash.length());
		}
	}
	/**
	 * @param url md5 time
	 */
	private void  buildURLIndex(String url,String docId)
	{
		MD5Kit md5Kit = new MD5Kit();
		String urlMd5 = md5Kit.md5(url);
		String rowKey = urlMd5;
	    long storageTime=System.currentTimeMillis(); 
	    String value = docId+"\t"+storageTime+"\t"+url;
		String redisKey=RedisConstant.KEY_PREFIX_VIDEO_URL_INDEX_TABLE+rowKey;
		RedisCrud.set(redisKey, value);
	}
	/**
	 * 抽取主机名和顶级域名
	 * @param url
	 * @return
	 */
	private URLInfo extractURLHost(String url){
		URLInfo urlInfo = new URLInfo();
		String host = HostDomainExtractKit.extractURLHost(url);
		String domain = HostDomainExtractKit.getTopLevelDomain(url);
		urlInfo.setHost(host);
		urlInfo.setDomain(domain);
		return urlInfo;
	}
}
