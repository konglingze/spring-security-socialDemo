package edu.nuc.security.core.validata.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import edu.nuc.security.core.propertie.SecurityConstants;
import edu.nuc.security.core.validata.code.dao.ValidateCodeProcessor;


@RestController
public class ValidateCodeController {

	/*// 设置全局静态session_key名称
	static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	//图形验证码生成器
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;
	//短型验证码生成器
	@Autowired
	private ValidateCodeGenerator smsCodeGenerator;
	//短型验证码发送器
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	
	// 操作session类
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	//图形验证码控制器
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 生成图形验证码
		// ImageCode imageCode = createImageCode(new ServletWebRequest(request));
		ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
		// 构造session request(session),key.value(放入session)
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		// 输出图片
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

	// 短信验证码
	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 生成短信验证码
		SmsCode smsCode = (SmsCode)smsCodeGenerator.generate(new ServletWebRequest(request));
		// 构造session request(session),key.value
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        //向用户发送短信验证码
		smsCodeSender.send(mobile, smsCode.getCode());
	}

	public static String getSessionKey() {
		return SESSION_KEY;
	}
*/
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	                     
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
	 * 
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			throws Exception {

		//logger.info("validateCodeProcessorHolder"+validateCodeProcessorHolder);
		
		// 此处调用abstract的抽象方法再用子类实现
		// create包含 generate，save，send三个方法
		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("调用create方法");
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
	}

}
