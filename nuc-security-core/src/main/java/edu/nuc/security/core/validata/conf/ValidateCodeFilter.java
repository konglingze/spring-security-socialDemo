package edu.nuc.security.core.validata.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.nuc.security.core.propertie.SecurityConstants;
import edu.nuc.security.core.propertie.SecurityProperties;

//确保过滤器只被调用一次
//设置调用验证码的图片拦截地址(初始化url的值)
//之前的校验逻辑方法在这里写
//之后挪到抽象实现类中去调用
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	/**
	 * 校验码失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 配置信息
	 */
	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * 系统中的校验码处理器
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();

	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	//private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("RequestURI " + request.getRequestURI());


/*
//		if (StringUtils.equals("/authentication/form", request.getRequestURI())
//			&& StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {

		boolean action = false;
		for (String url : urlMap) {
			if (pathMatcher.match(url, request.getRequestURI())) {
				action = true;
			}
		}
		if (action) {
			try {
				logger.info("开始校验");
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				// 不往下边走了直接抛出
				return;
			}
			logger.info("检验成功，返回其他拦截器栈");
		}
		// 调用其他过滤器链
		filterChain.doFilter(request, response);
*/

		// 获取校验方式
		//如果方式不为空，则说明需要进行校验(与之前匹配需要过滤的url类似)
		ValidateCodeType type = getValidateCodeType(request);
		
		logger.info("validateCodeProcessorHolder: "+validateCodeProcessorHolder);
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				logger.info("进入校验块");
				logger.info(validateCodeProcessorHolder.findValidateCodeProcessor(type).toString());
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				// 不往下边走了直接抛出
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	// 设置需要验证码的url
	@Override
	public void afterPropertiesSet() throws ServletException {

/*		super.afterPropertiesSet();
        //增加非空判断(掉了好几根头发，这里由于没有做判断，导致filter加载默认配置,构造bean抛出异常，
		//切出来的url为null报空指针异常，由于注解的关系还无法调试)
		if(securityProperties.getCode().getImage().getUrl()==null) {
			urlMap.add("/authentication/form");
			return;
		}
		
		String[] configUrls = StringUtils
				.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
		for (String configUrl : configUrls) {
			urlMap.add(configUrl);
		}
		urlMap.add("/authentication/form");*/
		// 放入图片验证默认路径
		//改造字段为静态可以较少的在维护过程中避免对公共模块的修改
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
		// 放入短信验证默认路径
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
	}

	/**
	 * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
	 * 
	 * @param urlString
	 * @param type
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}

	}

	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}


	// 在设置过滤器启动时需要显示注入AuthenticationFailureHandler
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public Map<String, ValidateCodeType> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, ValidateCodeType> urlMap) {
		this.urlMap = urlMap;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public ValidateCodeProcessorHolder getValidateCodeProcessorHolder() {
		return validateCodeProcessorHolder;
	}

	public void setValidateCodeProcessorHolder(ValidateCodeProcessorHolder validateCodeProcessorHolder) {
		this.validateCodeProcessorHolder = validateCodeProcessorHolder;
	}

}
