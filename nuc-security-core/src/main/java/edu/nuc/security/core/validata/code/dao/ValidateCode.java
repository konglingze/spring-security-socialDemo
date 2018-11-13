package edu.nuc.security.core.validata.code.dao;

import java.time.LocalDateTime;

//所有验证码的实例Bean接口
public interface ValidateCode {

	public boolean isExpried();

	public String getCode();
	
	public LocalDateTime getLcDateTime();
}
