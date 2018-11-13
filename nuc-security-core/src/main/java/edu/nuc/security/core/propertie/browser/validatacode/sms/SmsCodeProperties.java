
package edu.nuc.security.core.propertie.browser.validatacode.sms;

import edu.nuc.security.core.propertie.browser.validatacode.ValidateCodePropertiesDefault;

public class SmsCodeProperties implements ValidateCodePropertiesDefault {

	// 短信验证码的长度及过期时间

	private int length = 4;
	private int expireIn = 60;

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

}
