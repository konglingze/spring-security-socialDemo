package edu.nuc.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import edu.nuc.service.HelloService;

public class ValidateImpl implements ConstraintValidator<MyValidate, Object> {

	@Autowired//不用@component
	private HelloService helloService;

	@Override
	public void initialize(MyValidate constraintAnnotation) {
		System.out.println("初始化校验注解");
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		helloService.hello("konglingze");
		System.out.println(value);
		return false;// 校验成功或者失败
	}

}
