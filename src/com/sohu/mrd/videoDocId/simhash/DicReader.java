package com.sohu.mrd.videoDocId.simhash;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;
/**
 * 文档读入工具
 * 
 * @author Jin Guopan
 */
public final class DicReader {

	private static final Logger logger = Logger.getLogger(KeywordExtractor.class);

	private DicReader() {
	}
	/**
	 * 返回BufferedReader
	 * 
	 * @param name 文件名
	 * @return
	 */
	public static BufferedReader getReader(String name) {
		InputStream in = DicReader.class.getResourceAsStream("/" + name);
		try {
			return new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("编码格式不支持：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 返回InputStream
	 * @param name 文件名
	 * @return
	 */
	public static InputStream getInputStream(String name) {
		InputStream in = DicReader.class.getResourceAsStream("/" + name);
		return in;
	}
}
