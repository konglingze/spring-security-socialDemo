package edu.nuc.security.core.validata.mobile.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

//由于定位不同，手机验证码可以确认是注册本人在操作，所以不与图形验证码的逻辑放在一个模块中
//图形验证码只用于各个模块用户的登陆
//封装用户信息
//借鉴自spring-security UsernamePasswordAuthenticationToken
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Object principal;

	//未登陆成功之前保存的为手机号
	//登陆成功之后保存用户信息
	public SmsCodeAuthenticationToken(Object mobile) {
		super(null);
		this.principal = mobile;
		setAuthenticated(false);
	}

	public SmsCodeAuthenticationToken(Object principal, 
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}

	public Object getPrincipal() {
		return this.principal;
	}

	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

}
