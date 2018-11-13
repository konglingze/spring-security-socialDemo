package edu.nuc.security.core.qq.conn;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class QQOAuth2Template extends OAuth2Template {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 构造模板对象用于QQ登录验证
	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//添加client_id和client_secret参数，默认不添加
		setUseParametersForClientAuthentication(true);
	}

	// 修改QQ登录成功返回字符串
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

		logger.info("获取accessToken的响应" + responseStr);

		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

		String accessToken = StringUtils.substringAfterLast(items[0], "=");
		Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
		String refreshToken = StringUtils.substringAfterLast(items[3], "=");

		return new AccessGrant(accessToken, null, refreshToken, expiresIn);

	}

	// 添加对返回http的响应
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();

		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return super.createRestTemplate();
	}

}
