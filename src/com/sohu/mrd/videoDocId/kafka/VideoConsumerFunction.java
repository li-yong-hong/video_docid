package com.sohu.mrd.videoDocId.kafka;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.sohu.mrd.videoDocId.utils.FileKit;
/**
 * @author Jin Guopan
   @creation 2016年12月21日
      视频数据的处理类
 */
public class VideoConsumerFunction implements Function<byte[], Boolean> {
	private static final Logger LOG = Logger.getLogger(VideoConsumerFunction.class);
	Boolean flag =true;
	public Boolean apply(byte[] message) {
		try {
			String msgstr=new String(message);
			JSONObject  jsonObject= JSON.parseObject(msgstr);
			String title=jsonObject.getJSONObject("videoData").getString("video_title");
			String introduction = jsonObject.getJSONObject("videoData").getString("introduction");
			String playUrl = jsonObject.getJSONObject("videoData").getString("playUrl"); 
			StringBuilder sb = new StringBuilder();
			sb.append(playUrl);
			sb.append("\t");
			sb.append(title);
			sb.append("\t");
			sb.append(introduction);
			sb.append("\n");
			String writeStr=sb.toString();
			FileKit.write2File(writeStr, "data/kafkaData", true);
		} catch (Exception e) {
			LOG.error("解析视频json失败 ",e);
		}
		return flag;
	}
}
