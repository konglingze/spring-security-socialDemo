package edu.nuc.security.core.qq.conn;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import edu.nuc.security.core.qq.api.QQ;
import edu.nuc.security.core.qq.api.impl.QQImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;

	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

	private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String clientId, String clientSecret) {
		// app的用户和密码(由注册互联的应用提供,不同应用对应不同的一组值)
		// authorizeUrl将用户导向认证服务器
		// accessTokenUrl携带授权码申请令牌地址
		// 改为构造自己的登陆模板对象
		// super(new OAuth2Template(clientId, clientSecret, URL_AUTHORIZE,
		// URL_ACCESS_TOKEN));
		super(new QQOAuth2Template(clientId, clientSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = clientId;
	}

	@Override
	public QQ getApi(String accessToken) {

		return new QQImpl(accessToken, appId);
	}

}
