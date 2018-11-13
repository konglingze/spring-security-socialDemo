package edu.nuc.security.core.propertie.social.weixin;

import org.springframework.boot.autoconfigure.social.SocialProperties;

public class WeixinProperties extends SocialProperties {

	// 第三方Id
	private String providerId = "weixin";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
