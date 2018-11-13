package edu.nuc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

//从默认SpringSocialConfigurer中取出filter并个性化
public class nucSpringSocialConfigurer extends SpringSocialConfigurer {

	private String fileterPocessesUrl;

	public nucSpringSocialConfigurer(String fileterPocessesUrl) {
		this.fileterPocessesUrl = fileterPocessesUrl;
	}

	//不同应用的登陆回调路径不同
	@Override
	protected <T> T postProcess(T object) {

		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);

		filter.setFilterProcessesUrl(fileterPocessesUrl);
		return super.postProcess(object);
	}

}
