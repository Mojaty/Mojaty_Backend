package com.motivation.mojaty.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DomainAop implements Aop {

    @Pointcut("execution(* com.motivation.mojaty.domain..web.api..*.*(..))")
    private void cut() {}

    @Before("cut()")
    @Override
    public void before(JoinPoint joinPoint) {
        Method method = createMethodSignature(joinPoint);
        Object[] args = joinPoint.getArgs();
        log.info(">>>>>>>>>>starting method = {}", method.getName());
        for(Object arg : args) {
            log.info(">>>>>>>>>>parameter type = {}", arg.getClass().getSimpleName());
            log.info(">>>>>>>>>>parameter value = {}", args);
        }
    }

    @AfterReturning(value = "cut()", returning = "object")
    @Override
    public void after(JoinPoint joinPoint, Object object) {
        Method method = createMethodSignature(joinPoint);
        log.info(">>>>>>>>>>end method = {}", method.getName());
    }

    private Method createMethodSignature(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
}
