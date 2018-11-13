package edu.nuc.security.core.validata.code.dao;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import edu.nuc.security.core.validata.code.dao.impl.sms.SmsCode;
import edu.nuc.security.core.validata.conf.ValidateCodeException;
import edu.nuc.security.core.validata.conf.ValidateCodeType;



//除了抽象实现接口中的创建和校验两个方法之外
//抽象实现了处理检验验证码时需要的新方法(生成，保存，发送，获取验证码类型)-抽取建立公共调用
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 操作session工具类
	 */
	//将原先的filter中的validate方法调到此处进行调用
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 收集系统中的ValidateCodeGenerator接口的实现(开发技巧之依赖查找)
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Override
	public void create(ServletWebRequest request) throws Exception {

		
		logger.info("调用抽象三件套");
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		
		logger.info("调用抽象生成方法生成验证码");
		String type = getValidateCodeType(request).toString().toLowerCase();
		//type = type.substring(0, 1).toUpperCase()+type.substring(1);
		logger.info("type "+type);
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName().substring(8);
		logger.info("generatorName "+generatorName);
		
		for (Map.Entry<String, ValidateCodeGenerator> entry : validateCodeGenerators.entrySet()) {
			logger.info(entry.getKey() +" "+ entry.getValue() );
		}
		logger.info("取到的generator "+validateCodeGenerators.get(generatorName));
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		logger.info("ValidateCodeGenerator： "+validateCodeGenerator);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		//存入字符串而非图片，图片无法序列化
		ValidateCode code = new ValidateCode() {

			@Override
			public boolean isExpried() {
				return validateCode.isExpried();
			}

			@Override
			public String getCode() {
				return validateCode.getCode();
			}

			@Override
			public LocalDateTime getLcDateTime() {
				return validateCode.getLcDateTime();
			}
		};
		sessionStrategy.setAttribute(request, getSessionKey(request), code);
	}

	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		logger.info(" ValidateCodeType type: "+ type);
		logger.info(" return type"+ type.toUpperCase());
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		ValidateCodeType processorType = getValidateCodeType(request);
		String sessionKey = getSessionKey(request);

		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}
		// 验证成功后移除session
		sessionStrategy.removeAttribute(request, sessionKey);
	}

}
