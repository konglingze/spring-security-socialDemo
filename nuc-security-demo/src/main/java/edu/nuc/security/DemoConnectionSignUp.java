package edu.nuc.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		
	  //根据社交用户信息创建默认用户并返回用户唯一标识
		
		return connection.getDisplayName();
	}

}
