package edu.nuc.security.core.validata.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.nuc.security.core.propertie.SecurityProperties;
import edu.nuc.security.core.validata.code.dao.ValidateCodeGenerator;
import edu.nuc.security.core.validata.code.dao.impl.image.ImageCodeGenerator;
import edu.nuc.security.core.validata.code.dao.impl.sms.SmsCodeGenerator;
import edu.nuc.security.core.validata.code.dao.impl.sms.sender.DefaultSmsCodeSender;
import edu.nuc.security.core.validata.code.dao.impl.sms.sender.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	// 以增量的方式去适应变化(在-demo模块中可以进行代码扩展，扩展方式为在对应接口的实现类下面添加
	// @Component("imageCodeGenerator")注解将此默认实现覆盖)
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator() {

		ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
		imageCodeGenerator.setSecurityProperties(securityProperties);

		return imageCodeGenerator;
	}

	@Bean
	// 以增量的方式去适应变化(在-demo模块中可以进行代码扩展，扩展方式为在对应接口的实现类下面添加
	// @Component("smsCodeGenerator")注解将此默认实现覆盖)
	@ConditionalOnMissingBean(name = "smsCodeGenerator")
	public ValidateCodeGenerator smsCodeGenerator() {

		SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
		smsCodeGenerator.setSecurityProperties(securityProperties);

		return smsCodeGenerator;
	}

	@Bean
	// 以增量的方式去适应变化(在-demo模块中可以进行代码扩展，扩展方式为在对应接口的实现类下面添加
	// @Component("smsCodeSender")注解将此默认实现覆盖)
	@ConditionalOnMissingBean(SmsCodeGenerator.class)
	public SmsCodeSender smsCodeSender() {

		SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();

		return smsCodeSender;
	}

}
