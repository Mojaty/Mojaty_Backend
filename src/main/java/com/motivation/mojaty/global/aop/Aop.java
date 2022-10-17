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
public class Aop {

    @Pointcut("execution(* com.motivation.mojaty.domain..web.api..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        Method method = createMethodSignature(joinPoint);
        log.info(">>>>>>>>>>starting method = {}", method.getName());

        Object[] args = joinPoint.getArgs();

        for(Object obj : args) {
            log.info("type = {}", obj.getClass().getSimpleName());
            log.info("value : " + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "object")
    public void after(JoinPoint joinPoint, Object object) {
        Method method = createMethodSignature(joinPoint);
        log.info(">>>>>>>>>>end method = {}", method.getName());
        log.info(">>>>>>>>>>type = {}", object.getClass().getSimpleName());
    }

    private Method createMethodSignature(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
}
