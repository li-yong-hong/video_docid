package com.sohu.mrd.videoDocId.duplicate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.videoDocId.constant.Constant;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.hbase.QueryService;
import com.sohu.mrd.videoDocId.model.DocSimilaryInfo;
import com.sohu.mrd.videoDocId.utils.JaccardSimilaryKit;
import com.sohu.mrd.videoDocId.utils.SimHashKit;
/**
 * @author  Jin Guopan
 * @version 2016-11-22
 */
public class TitleContentDuplicate{
	public static final Logger LOG=Logger.getLogger(TitleContentDuplicate.class);
	/**
	 * 通过标题和内容的simhash 值进行排重
	 * @param simHash
	 * @param title
	 * @param content
	 * @return
	 */
	public String  duplicateByTitleContent(String simHash,String title,String content )
	{
		String  flag = Constant.DUPLICATE_FLAG;
		List<DocSimilaryInfo>  similaryDocs=duplicateTitleContentBySimhash(title, content, simHash, HBaseConstant.VIDEO_CONTENT_INDEX_TABLE);
		if(similaryDocs.size()==0)
		{
			return flag;
		}else{
			TreeMap<String,String> treeMap = new TreeMap<String, String>();
			for(int i=0;i<similaryDocs.size();i++)
			{
				DocSimilaryInfo docSimilaryInfo = similaryDocs.get(i);
				String simContent=docSimilaryInfo.getContent();
				String docId = docSimilaryInfo.getDocId();
				double contentSimilary=docSimilaryInfo.getContentSimilary();
				double titleSimilary=docSimilaryInfo.getTitleSimilary();
				int distance=docSimilaryInfo.getDistance();
				String simTitle=docSimilaryInfo.getTitle();
				StringBuilder keySb = new StringBuilder();
				int subDistance = 10-distance;
				keySb.append(contentSimilary);
				keySb.append("&");
				keySb.append(titleSimilary);
				keySb.append("&");
				keySb.append(subDistance);
				keySb.append("&");
				keySb.append(i); //防止 三个条件相同，覆盖 已有的docId
				treeMap.put(keySb.toString(), docId);
			}
		    //取得最相似的那个docId
			Entry<String, String>    treeEntry = treeMap.lastEntry();
			String similary=treeEntry.getKey();
			String docId = treeEntry.getValue();
			StringBuilder logSb = new StringBuilder();
			logSb.append(similary);
			logSb.append("\t");
			logSb.append(docId);
			String log = logSb.toString();
			LOG.info("通过content 进行排重 "+log);
			flag = docId;
		}
		return flag;
	}
	/**
	 * simhash排重
	 * @param content
	 * @param contentSimhash
	 * @return
	 */
    private List<DocSimilaryInfo>  duplicateTitleContentBySimhash(String title,String content,String contentSimhash,String tableName)
    {
    	String simhashFragment1=contentSimhash.substring(0, 16);
		String simhashFragment2=contentSimhash.substring(16, 32);
		String simhashFragment3=contentSimhash.substring(32, 48);
		String simhashFragment4=contentSimhash.substring(48, 64);
		String simhashValues1=QueryService.getHBaseByRadomRowkey(tableName, simhashFragment1);
		String simhashValues2=QueryService.getHBaseByRadomRowkey(tableName, simhashFragment2);
		String simhashValues3=QueryService.getHBaseByRadomRowkey(tableName, simhashFragment3);
		String simhashValues4=QueryService.getHBaseByRadomRowkey(tableName, simhashFragment4);
		StringBuilder sb = new StringBuilder();
		if(simhashValues1!=null)
		{
			sb.append(simhashValues1);
			sb.append("\001");
		}
		if(simhashValues2!=null)
		{
			sb.append(simhashValues2);
			sb.append("\001");
		}
		if(simhashValues3!=null)
		{
			sb.append(simhashValues3);
			sb.append("\001");
		}
		if(simhashValues4!=null)
		{
			sb.append(simhashValues4);
		}
		String simhashValues = sb.toString();
		List<DocSimilaryInfo>  contentDocSimilarys=getSimilaryInfo(simhashValues, contentSimhash, title, content);
		return contentDocSimilarys;
    }
    /**
     * 根据查找出来的四段simhash值进行相似度判断
     * @param simhashValues
     * @param contentSimhash
     * @param title
     * @param content
     * @return
     */
    private List<DocSimilaryInfo>  getSimilaryInfo(String simhashValues,String contentSimhash,String title,String content)
    {
    	List<DocSimilaryInfo> docSimilaryInfos = new ArrayList<DocSimilaryInfo>();
    	if(!simhashValues.trim().equals("")&&simhashValues.length()>0)
    	{
    		String[]  contentdocIds=simhashValues.split("\001", -1);
    		for(int i=0;i<contentdocIds.length;i++)
    		{
    			String  contentDocId  = contentdocIds[i];
    			String[] contentDocIdArray=contentDocId.split("#&", -1);
    			if(contentDocIdArray.length>=4)
    			{
    				String  tempTitle = contentDocIdArray[0];
        			String  tempContent   = contentDocIdArray[1];
        			String  tempDocId   = contentDocIdArray[2];
        			String  tempSimhash = contentDocIdArray[3];
        			//取得汉明距离
        			int distance = SimHashKit.getHanMingDistance(contentSimhash, tempSimhash);
        			if(distance<=3)
        			{
        				//判断jaccared 相似度
        				double contentsimilary =JaccardSimilaryKit.getJaccardSimilarity(tempContent, content);
        				double titlesimilary = JaccardSimilaryKit.getJaccardSimilarity(tempTitle, title);
        				
        				if(contentsimilary>=0.85 && titlesimilary >=0.85)
        				{
        					DocSimilaryInfo contentDocSimilary = new DocSimilaryInfo();
        					contentDocSimilary.setDistance(distance);
        					contentDocSimilary.setTitle(tempTitle);
        					contentDocSimilary.setContent(tempContent);
        					contentDocSimilary.setDocId(tempDocId);
        					contentDocSimilary.setContentSimilary(contentsimilary);
        					contentDocSimilary.setTitleSimilary(titlesimilary);
        					docSimilaryInfos.add(contentDocSimilary);
        				}
        			}
    			}
    			
    		}
    	}
		return docSimilaryInfos;
    }
}
