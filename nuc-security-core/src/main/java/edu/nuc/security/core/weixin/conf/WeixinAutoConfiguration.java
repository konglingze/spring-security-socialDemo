/**
 * 
 */
package edu.nuc.security.core.weixin.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import edu.nuc.security.core.propertie.SecurityProperties;
import edu.nuc.security.core.propertie.social.weixin.WeixinProperties;
import edu.nuc.security.core.social.nucConnectView;
import edu.nuc.security.core.weixin.conn.WeixinConnectionFactory;

/**
 * 微信登录配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
	 * #createConnectionFactory()
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}
	
	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new nucConnectView();
	}
	
}
