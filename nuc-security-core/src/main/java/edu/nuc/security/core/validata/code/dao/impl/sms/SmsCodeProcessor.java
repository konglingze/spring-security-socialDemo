package edu.nuc.security.core.validata.code.dao.impl.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import edu.nuc.security.core.propertie.SecurityConstants;
import edu.nuc.security.core.validata.code.dao.AbstractValidateCodeProcessor;
import edu.nuc.security.core.validata.code.dao.ValidateCode;
import edu.nuc.security.core.validata.code.dao.impl.sms.sender.SmsCodeSender;

@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	
	//短型验证码发送器
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		smsCodeSender.send(mobile, validateCode.getCode());
	}



}
