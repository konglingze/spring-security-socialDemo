package edu.nuc.security.core.propertie.social;

import edu.nuc.security.core.propertie.social.qq.QQProperties;
import edu.nuc.security.core.propertie.social.weixin.WeixinProperties;

public class SocialProperties {

	private String fileterPocessesUrl = "/auth";

	private QQProperties qq = new QQProperties();

	private WeixinProperties weixin = new WeixinProperties();

	public WeixinProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinProperties weixin) {
		this.weixin = weixin;
	}

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFileterPocessesUrl() {
		return fileterPocessesUrl;
	}

	public void setFileterPocessesUrl(String fileterPocessesUrl) {
		this.fileterPocessesUrl = fileterPocessesUrl;
	}

}
