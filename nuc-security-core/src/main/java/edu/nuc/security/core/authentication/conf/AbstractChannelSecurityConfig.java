package edu.nuc.security.core.authentication.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import edu.nuc.security.core.propertie.SecurityConstants;

//默认公共配置启动模块
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

	// 注入自定义登陆处理器
	@Autowired
	protected AuthenticationSuccessHandler nucAuthenticationSuccessHandler;

	// 拦截器失败处理器
	@Autowired
	protected AuthenticationFailureHandler nucAuthenticationFailHandler;

	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
		        .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)// "/authentication/require"
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)// "/authentication/form"
				.successHandler(nucAuthenticationSuccessHandler)// 自定义登陆成功处理器
				.failureHandler(nucAuthenticationFailHandler);// 自定义登陆失败处理器

	}

}
