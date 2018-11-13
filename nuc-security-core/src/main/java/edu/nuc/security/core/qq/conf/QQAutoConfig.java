package edu.nuc.security.core.qq.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import edu.nuc.security.core.propertie.SecurityProperties;
import edu.nuc.security.core.propertie.social.qq.QQProperties;
import edu.nuc.security.core.qq.conn.QQConnectionFactory;

@Configuration
//当properties文件中app-Id被配置时edu.nuc.security.social.qq之后所有的关联配置起作用
@ConditionalOnProperty(prefix = "edu.nuc.security.social.qq", name = "app-Id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqProperties = securityProperties.getSocial().getQq();

		return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(),
				qqProperties.getAppSecret());
	}

}
