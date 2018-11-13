package edu.nuc.security.core.validata.code.dao.impl.sms.sender;

public interface SmsCodeSender {

	void send(String mobile ,String code);
	
}
