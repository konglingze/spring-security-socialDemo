package edu.nuc.security.core.validata.code.dao;

import org.springframework.web.context.request.ServletWebRequest;


//所有验证码校验执行调用处理器接口(由Controller直接调用其抽象实现)
//只包含创建和校验两个抽象方法
public interface ValidateCodeProcessor {
	/**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}
