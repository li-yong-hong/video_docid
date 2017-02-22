package com.sohu.mrd.videoDocId.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * @author jinguopan
 * 设置编码过滤器
 */
public class EncodeFileter implements Filter {
	 String encoding="";
	 private FilterConfig filterConfig;
	public void destroy() {
		filterConfig=null;
		encoding=null;
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		filterChain.doFilter(request, response);
	}
	//过滤器初始化，并从web.xml得到编码格式
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
		encoding=filterConfig.getInitParameter("ecoding");
	}

}
