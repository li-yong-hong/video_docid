package com.sohu.mrd.videoDocId.duplicate;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.videoDocId.constant.Constant;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.hbase.QueryService;
import com.sohu.mrd.videoDocId.utils.MD5Kit;
/**
 * @author  Jin Guopan
 * @version 2016-11-22
 */
public class URLDuplicate {
	public static final Logger LOG=Logger.getLogger(TitleContentDuplicate.class);
	public static String duplicatebyURL(String url)
	{
		String flag=Constant.DUPLICATE_FLAG;
		MD5Kit md5Kit = new MD5Kit();
		String rowKey= md5Kit.md5(url);
		String value=QueryService.getHbase(HBaseConstant.VIDEO_URL_INDEX_TABLE, rowKey);
		if(value!=null)
		{
			String[] values=value.split("\t", -1);
			String docId = values[0];
			String storageTime=values[1];
			String valueUrl=values[2];
		    flag = docId;
		    LOG.info("通过url 排重成功"+"docId "+docId+"原始 url "+valueUrl+"现在url "+url);
		}
		return flag;
	}
}
