package edu.nuc.security.browser.support;

/**
 * 
 * <p>
 * Title: SocialUserInfo
 * </p>
 * <p>
 * Description:用于社交用户通过社交登陆后返回用户详细信息用于回掉和生成本地用户基本信息和唯一ID
 * </p>
 * 
 * @author konglingze
 * @date 2018年9月25日
 */
public class SocialUserInfo {
	// 第三方用户来源
	private String providerId;
	// 第三方用户openId
	private String providerUserId;
	// 用户的昵称
	private String nickName;
	// 用户的头像
	private String headimg;

	// geter seter方法

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
