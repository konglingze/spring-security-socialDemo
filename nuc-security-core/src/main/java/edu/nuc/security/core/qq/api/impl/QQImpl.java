package edu.nuc.security.core.qq.api.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nuc.security.core.qq.api.QQ;
import edu.nuc.security.core.qq.api.QQUserInfo;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	// 处理oauth协议中的三个参数openId,oauth_consumer_key(appId),accessToken

	// 获取openId
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	// 获取用户信息
	private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?&oauth_consumer_key=%s&openid=%s";

	private String appId;

	private String openId;

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 工具类用于对象转化
	private ObjectMapper objectMap = new ObjectMapper();

	public QQImpl(String accessToken, String appId) {
		// 执行父类验证模板构造方法
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		logger.info("执行父类验证模板构造方法");
		this.appId = appId;

		// 拼接accssToken和请求
		String url = String.format(URL_GET_OPENID, accessToken);

		// 发送请求并返回json
		String result = getRestTemplate().getForObject(url, String.class);
		System.out.println(result);

		// 根据返回的json截取openId
		this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");

	}

	// 获取用户信息
	@Override
	public QQUserInfo getUserInfo() {

		String url = String.format(URL_GET_USER_INFO, appId, openId);

		String result = getRestTemplate().getForObject(url, String.class);

		QQUserInfo userInfo = null;
		try {
			userInfo = objectMap.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
			
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败");

		}
	}
}
