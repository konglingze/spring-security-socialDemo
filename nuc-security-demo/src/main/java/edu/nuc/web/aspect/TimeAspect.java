package edu.nuc.web.aspect;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * 
 * @author 孔先生
 * 拦截机制(方盒层级机制)
 * filter可以拿到请求信息
 * intercepter可以拿到请求信息，也能拿到处理方法的信息，但无法得到形参
 * aspect能拿到方法的形参，但拿不到调用方法的请求信息
 */
//@Aspect
//@Component
public class TimeAspect {

	@Around("execution(* edu.nuc.web.controller.UserContorller.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

		System.out.println("time aspect start");
		long start = new Date().getTime();

		Object obj = pjp.proceed();
		System.out.println("拿到handler参数");
		Object[] args = pjp.getArgs();
		for (Object object : args) {

			System.out.println("参数为 " + object);
		}

		System.out.println("耗时 " + (new Date().getTime() - start));
		System.out.println("time aspect end");

		return obj;

	}

}
