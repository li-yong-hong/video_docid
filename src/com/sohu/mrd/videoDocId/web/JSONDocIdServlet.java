package com.sohu.mrd.videoDocId.web;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.videoDocId.service.GenerateDocIdService;
/**
 * @author Jin Guopan
 * @version 2016-12-16
 */
public class JSONDocIdServlet extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(JSONDocIdServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String json = request.getParameter("json"); // 接受前端json数据
		JSONObject result = new JSONObject();
		result.put("status", "success");
		if (json.trim().equals("") || null == json) {
			result.put("status", "fail");
			result.put("msg", "json is null");
		} else {
			JSONObject jsonObject = JSON.parseObject(json);
			String url = jsonObject.getString("url");
			String source = jsonObject.getString("source");
			String content = jsonObject.getString("content");
			String content_length = jsonObject.getString("content_length");
			String title_length = jsonObject.getString("title_length");
			String media = jsonObject.getString("media");
			String publish_time = jsonObject.getString("publish_time");
			String title = jsonObject.getString("title");
			GenerateDocIdService  generateDocIdService =GenerateDocIdService.getInstance();
			LOG.info("前端接受的参数  title"+title+"\t"+"content"+content+"url "+url);
			String docId=generateDocIdService.getDocId(url, title, content);
			result.put("docId", docId);
			write2Client(result.toJSONString(),response);
		}
	}
	/**
	 * 向前端写入数据
	 * 
	 * @throws IOException
	 */
	public void write2Client(String vlaue, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(vlaue);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
