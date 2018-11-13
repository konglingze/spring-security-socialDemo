package edu.nuc.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//@Component
public class TimeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

		System.out.println("初始化filter 拦截");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		System.out.println("时间 拦截 启动");
		long startTime = new Date().getTime();
		chain.doFilter(request, response);

		System.out.println("时间 间隔 " + (new Date().getTime() - startTime));
		System.out.println("时间 拦截 关闭");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
