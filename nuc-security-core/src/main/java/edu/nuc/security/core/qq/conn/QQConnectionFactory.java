package edu.nuc.security.core.qq.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import edu.nuc.security.core.qq.api.QQ;

//包含serviceProvider和apiAdapter
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public QQConnectionFactory(String providerId, String appId, String appSecret) {
		// providerId, serviceProvider, apiAdapter)
		// 服务提供商标识
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
		logger.info("构造"+QQConnectionFactory.class);

	}

}
