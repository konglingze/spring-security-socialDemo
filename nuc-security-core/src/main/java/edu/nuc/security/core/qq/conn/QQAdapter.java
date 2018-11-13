package edu.nuc.security.core.qq.conn;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import edu.nuc.security.core.qq.api.QQ;
import edu.nuc.security.core.qq.api.QQUserInfo;

public class QQAdapter implements ApiAdapter<QQ> {

	// 测试服务是否可用
	@Override
	public boolean test(QQ api) {
		// TODO Auto-generated method stub
		return true;
	}

	// 适配security标准类和QQ
	@Override
	public void setConnectionValues(QQ qqApi, ConnectionValues values) {

		try {
			QQUserInfo userInfo = qqApi.getUserInfo();
			// 适配标准类
			values.setDisplayName(userInfo.getNickname());// QQ名
			values.setImageUrl(userInfo.getFigureurl_qq_1());// 头像
			values.setProfileUrl(null);// 个人主页
			values.setProviderUserId(userInfo.getOpenId());// 应用用户在登陆服务提供商(QQ)的唯一识别码openId

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		// 个人主页
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {

		// 发消息（更新微博等）
	}

}
