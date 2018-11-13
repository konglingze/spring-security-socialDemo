package edu.nuc.security.core.social;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import edu.nuc.security.core.propertie.SecurityProperties;

//利用社交网站登录配置
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired(required = false)
	private ConnectionSignUp connectionSignUp;

	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * @Bean public SpringSocialConfigurer nucSocialSecurityConfig() {
	 * logger.info("nucSocialSecurityConfig" +"初始化"); return new
	 * SpringSocialConfigurer(); };
	 */
	@Bean
	public SpringSocialConfigurer nucSocialSecurityConfig() {
		logger.info("nucSocialSecurityConfig" + "初始化");
		nucSpringSocialConfigurer configurer = new nucSpringSocialConfigurer(
				securityProperties.getSocial().getFileterPocessesUrl());
		// 用户身份无法确认时转至用户注册页面
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
		return configurer;
	};

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {

		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setTablePrefix("nuc_");
		// 添加默认用户创建逻辑(社交后自动注册用户)
		if (connectionSignUp != null) {
			repository.setConnectionSignUp(connectionSignUp);
		}
		// return super.getUsersConnectionRepository(connectionFactoryLocator);
		// connectionFactoryLocator查找ConnectionFactory(多个)
		// textEncryptor加解密敏感数据(此处不变为原值)
		return repository;
	}

	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator,
				getUsersConnectionRepository(connectionFactoryLocator));
	}

}
