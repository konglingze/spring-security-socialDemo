/**
 * 
 */
package edu.nuc.security.core.weixin.api;

/**
 * 微信API调用接口
 * 
 * @author konglingze
 *
 */
public interface Weixin {


	WeixinUserInfo getUserInfo(String openId);
	
}
