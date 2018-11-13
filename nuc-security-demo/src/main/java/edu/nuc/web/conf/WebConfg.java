package edu.nuc.web.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.nuc.web.filter.TimeFilter;
import edu.nuc.web.intercepter.TimeIntercepter;

/**
 * 
 * @author konglingze 
 * 自定义第三方拦截器配置和生效位置
 */
//@Configuration
public class WebConfg extends WebMvcConfigurerAdapter {

	//自定义intercepter
	//@Autowired
	private TimeIntercepter timeIntercepter;

	//@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeIntercepter);
	}

	//@Bean
	public FilterRegistrationBean timeFilter() {

		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		TimeFilter timeFilter = new TimeFilter();
		registrationBean.setFilter(timeFilter);

		List<String> urls = new ArrayList<>();

		urls.add("/*");
		registrationBean.setUrlPatterns(urls);
		return registrationBean;

	}

/*	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configureAsyncSupport(configurer);
		
		configurer.registerDeferredResultInterceptors(interceptors){
			//可以添加异步线程池
			//异步拦截请求
		}
	}*/
	
	
}
