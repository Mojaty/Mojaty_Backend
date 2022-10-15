package com.motivation.mojaty.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DomainAop implements Aop {

    @Pointcut("execution(* com.motivation.mojaty.domain..service..*.*(..))")
    private void cut() {}

    @Before("cut()")
    @Override
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info(">>>>>>>>>>starting method = {}", method.getName());
    }

    @AfterReturning("cut()")
    @Override
    public void after(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info(">>>>>>>>>>end method = {}", method.getName());
    }
}
