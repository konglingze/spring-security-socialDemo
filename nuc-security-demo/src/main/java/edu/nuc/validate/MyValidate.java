package edu.nuc.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME) // 运行时注解
@Constraint(validatedBy = ValidateImpl.class) // 校验注解
public @interface MyValidate {
	//校验失败所发的信息
	String message() default "校验失败";
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
