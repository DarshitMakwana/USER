/**************************************************
 * Copyright (c) 2022 Techforce Infotech Pvt Ltd. All rights reserved.
 *
 * @author: Techforce Infotech Pvt Ltd
 *
 **************************************************/
package com.userservice.userservice.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(public * com.userservice.userservice.*.*.*(..))")
	public Object aroundLog(final ProceedingJoinPoint j) throws Throwable {

		log.info(" " + j.getSignature().getDeclaringTypeName() + " : " + j.getSignature().getName() + " : START ");
		final Object value = j.proceed();

		log.info(" " + j.getSignature().getDeclaringTypeName() + " : " + j.getSignature().getName() + " : END ");
		return value;
	}

	@AfterThrowing(pointcut = "execution(public * com.userservice.userservice.*.*.*(..))", throwing = "error")
	public void logAfterThrowing(final JoinPoint j, final Throwable error) {

		log.error(" " + j.getSignature().getDeclaringTypeName() + " : " + j.getSignature().getName() + " : Exception : " + error.getMessage() + error);
	}


}
