package edu.nuc.security.core.validata.code.dao.impl.sms;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.nuc.security.core.validata.code.dao.ValidateCode;

//短信验证码dto层
public class SmsCode implements ValidateCode ,Serializable{

	private String code;

	// 过期时间
	private LocalDateTime lcDateTime;

	public SmsCode(String code, int expectIn) {
		super();
		this.code = code;
		// 未来时间(+60秒)
		this.lcDateTime = LocalDateTime.now().plusSeconds(expectIn);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getLcDateTime() {
		return lcDateTime;
	}

	public void setLcDateTime(LocalDateTime lcDateTime) {
		this.lcDateTime = lcDateTime;
	}

	// 是否过期
	public boolean isExpried() {
		return LocalDateTime.now().isAfter(lcDateTime);
	}

}
