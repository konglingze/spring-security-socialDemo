package edu.nuc.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nuc.security.browser.support.SimpleResponse;
import edu.nuc.security.core.propertie.SecurityProperties;
import edu.nuc.security.core.propertie.browser.LoginType;

@Component("nucAuthenticationFailHandler")
// public class NucAuthenticationFailHandler implements
// AuthenticationFailureHandler {
public class NucAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.info("登陆失败");

		// 设置处理失败
		response.setStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR.value());

		if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			//返回错误拦截器栈
			//response.getWriter().write(objectMapper.writeValueAsString(exception));
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		} else {
			//跳页面
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
