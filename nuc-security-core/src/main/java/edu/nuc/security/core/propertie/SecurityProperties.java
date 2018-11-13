package edu.nuc.security.core.propertie;

import org.springframework.boot.context.properties.ConfigurationProperties;

import edu.nuc.security.core.propertie.browser.BrowserProperties;
import edu.nuc.security.core.propertie.browser.validatacode.ValidateCodeProperties;
import edu.nuc.security.core.propertie.social.SocialProperties;

@ConfigurationProperties(prefix = "edu.nuc.security")
public class SecurityProperties {

	BrowserProperties browser = new BrowserProperties();

	private ValidateCodeProperties code = new ValidateCodeProperties();

	private SocialProperties social = new SocialProperties();

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties socialProperties) {
		this.social = socialProperties;
	}

}
