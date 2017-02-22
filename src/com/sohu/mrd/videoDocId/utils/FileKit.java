package com.sohu.mrd.videoDocId.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Jin Guopan
 * @creation 2016年11月29日
 */
public class FileKit {
	public static final Logger LOG = Logger.getLogger(FileKit.class);
	public static List<String> filePath = new ArrayList<String>();

	public static void main(String[] args) {
		File file = new File("data/test_delete");
		if (file.exists()) {
			file.delete();
			LOG.info("删除文件成功");
		}
	}

	/**
	 * 把字符串转换为0
	 * @param inStr
	 * @return
	 */
	public static InputStream Str2Inputstr(String inStr) {
		try {
			// return new ByteArrayInputStream(inStr.getBytes());
			// return new ByteArrayInputStream(inStr.getBytes("UTF-8"));
			return new StringBufferInputStream(inStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 把流转换为List<String>
	 * 
	 * @param is
	 * @return
	 */
	public static List<String> is2String(InputStream is) {
		List<String> list = new ArrayList<String>();
		if (is != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String temp = "";
			try {
				while ((temp = br.readLine()) != null) {
					list.add(temp);
				}
			} catch (IOException e) {
				LOG.error("读取文件行错误 " + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 文件每行转换为list
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> read2List(String path) {
		FileInputStream fis = null;
		List<String> list = new ArrayList<String>();
		try {
			fis = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = "";
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 读取文件按照行读取
	 * 
	 * @param path
	 * @return
	 */
	public static String read2String(String path) {
		String content = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String temp = "";
			StringBuilder sb = new StringBuilder();
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			content = sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("文件读入string错误 " + e.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * 递归遍历文件返回文件路径
	 * 
	 * @param InputPath
	 * @return
	 */
	public static List<String> recurReadFile(String inputPath) {
		File file = new File(inputPath);
		if (!file.isFile()) // 如果不是文件查找下级目录
		{
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File subFile = files[i];
				if (subFile.isDirectory()) {
					String path = subFile.getAbsolutePath();
					recurReadFile(path);
				} else if (subFile.isFile()) {
					String absFilePath = subFile.getAbsolutePath();
					// LOG.info("path "+absFilePath);
					filePath.add(absFilePath);
				} else {
					continue;
				}
			}
		} else { // 如果是文件，直接添加到list里面
			filePath.add(file.getAbsolutePath());
		}
		return filePath;
	}

	/**
	 * 以追加的方式按行进行写入文件
	 */
	public static void write2File(String s, String path) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path, true);
			StringBuilder sb = new StringBuilder();
			sb.append(s);
			sb.append("\n");
			fos.write(sb.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 是否追加写入文件
	 * 
	 * @param s
	 * @param path
	 * @param isAppend
	 */
	public static void write2File(String s, String path, boolean isAppend) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path, isAppend);
			fos.write(s.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
