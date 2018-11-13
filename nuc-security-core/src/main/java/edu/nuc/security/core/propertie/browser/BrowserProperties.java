package edu.nuc.security.core.propertie.browser;

import edu.nuc.security.core.propertie.session.SessionProperties;

public class BrowserProperties {

	// 默认注册页
	private String signUpUrl = "/def-nuc-signUp.html";

	// 默认登录页
	private String loginPage = "/def-nuc-login.html";

	// 设置登陆成功和失败是处理器的返回页面默认枚举选择
	private LoginType loginType = LoginType.JSON;

	// 设置记住我-超时时间-默认一小时
	private int rememberMeSeconds = 3600;

	// 设置session的处理
	private SessionProperties session = new SessionProperties();

	public SessionProperties getSession() {
		return session;
	}

	public void setSession(SessionProperties session) {
		this.session = session;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

}
