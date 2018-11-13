package edu.nuc.security.browser.conf;

import javax.sql.DataSource;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import edu.nuc.security.browser.session.NucExpiredSessionStrategy;
import edu.nuc.security.core.authentication.conf.AbstractChannelSecurityConfig;
import edu.nuc.security.core.authentication.conf.ValidateCodeSecurityConfig;
import edu.nuc.security.core.propertie.SecurityConstants;
import edu.nuc.security.core.propertie.SecurityProperties;
import edu.nuc.security.core.validata.mobile.authentication.conf.SmsCodeAuthenticationSecurityConfig;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

	// private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Autowired
	private SpringSocialConfigurer nucSocialSecurityConfig;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	
	// 数据源
	@Autowired
	private DataSource dataSource;
	// 用于调用记住我之后调用获取登陆服务(UserDetailsService已经在登陆验证的时候用MyUserDetails实现)

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	// 密码加盐转码
	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	// 记住我repository配置
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		// 设置数据源
		tokenRepository.setDataSource(dataSource);
		// 设置数据源参数
		tokenRepository.setCreateTableOnStartup(false);

		return tokenRepository;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		applyPasswordAuthenticationConfig(http);//加载公共模块配置

		//validateCodeFilter.setAuthenticationFailureHandler(nucAuthenticationFailHandler);
		//validateCodeFilter.setSecurityProperties(securityProperties);
		//validateCodeFilter.afterPropertiesSet();
		
		http.apply(validateCodeSecurityConfig)//加载图形验证码配置
		        .and()
		    .apply(smsCodeAuthenticationSecurityConfig)//加载短信验证码配置
				.and()
			.apply(nucSocialSecurityConfig)	//加载QQ社交登陆配置
			    .and()
				//加载浏览器配置
			.rememberMe()// 记住我配置
				.tokenRepository(persistentTokenRepository())// 添加repository
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())// 配置超时时间
				.userDetailsService(userDetailsService)// 调取登陆方法实现
				.and()
				//session过期跳转
			.sessionManagement()
			    //过期session处理
			    .invalidSessionStrategy(invalidSessionStrategy)
			    //.invalidSessionUrl("/session/invalid")
			    //最大session数量
			    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
			    //阻止并发登陆行为
			    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
			    .expiredSessionStrategy(sessionInformationExpiredStrategy)
			    .and()
			    .and()
				//加载授权配置
			.authorizeRequests()// 身份认证配置
				.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
						SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
						securityProperties.getBrowser().getLoginPage(),
						SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
						securityProperties.getBrowser().getSignUpUrl(),
						securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
						"/user/regist")
				        .permitAll()
				.anyRequest()// 需要授权
				.authenticated()// 需要身份验证
				.and()
			.csrf().disable();// 关闭CSRF
				
	}
}
