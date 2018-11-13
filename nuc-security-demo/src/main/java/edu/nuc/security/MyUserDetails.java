package edu.nuc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetails implements UserDetailsService, SocialUserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PasswordEncoder passwordEncoder;

	// @Autowired
	// 根据用户名查询用户信息
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("表单登陆用户");
		return buildUser(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

		logger.info("社交快捷登陆登陆用户");
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String username) {
		// 联系数据库查找用户密码
		logger.info("登陆用户名 " + username);
		String password = passwordEncoder.encode("123456");
		logger.info("密码是 " + password);
		// 用户密码权限
		return new SocialUser(username, password// 注册时的动作
				, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
	}

}
