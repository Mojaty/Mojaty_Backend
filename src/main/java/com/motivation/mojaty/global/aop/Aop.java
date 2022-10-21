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
    private void domainCut() {}

    @Pointcut("execution(* com.motivation.mojaty.global..*.*(..))")
    private void globalCut() {}

    private static final String STARTING_METHOD_MESSAGE = ">>>>>>>>>>starting method = {}";
    private static final String END_METHOD_MESSAGE = ">>>>>>>>>>end method = {}";
    private static final String TYPE_MESSAGE = "type = {}";
    private static final String VALUE_MESSAGE = "value = {}";

    @Before("domainCut()")
    public void domainBefore(JoinPoint joinPoint) {
        Method method = createMethodSignature(joinPoint);
        log.info(STARTING_METHOD_MESSAGE, method.getName());

        Object[] args = joinPoint.getArgs();

        for(Object obj : args) {
            log.info(TYPE_MESSAGE, obj.getClass().getSimpleName());
            log.info(VALUE_MESSAGE, obj);

        }
    }

    @Before("globalCut()")
    public void globalBefore(JoinPoint joinPoint) {
        Method method = createMethodSignature(joinPoint);
        log.info(STARTING_METHOD_MESSAGE, method.getName());

        Object[] args = joinPoint.getArgs();

        for(Object obj : args) {
            log.info(TYPE_MESSAGE, obj.getClass().getSimpleName());
            log.info(VALUE_MESSAGE, obj);
        }
    }

    @AfterReturning(value = "domainCut()", returning = "object")
    public void domainAfter(JoinPoint joinPoint, Object object) {
        Method method = createMethodSignature(joinPoint);
        log.info(END_METHOD_MESSAGE, method.getName());
        if(object != null) {
            log.info(TYPE_MESSAGE, object.getClass().getSimpleName());
        }
        else {
            log.info(">>>>>>>>>>type = void");
        }
    }

    @AfterReturning(value = "globalCut()", returning = "object")
    public void globalAfter(JoinPoint joinPoint, Object object) {
        Method method = createMethodSignature(joinPoint);
        log.info(END_METHOD_MESSAGE, method.getName());
        if(object != null) {
            log.info(TYPE_MESSAGE, object.getClass().getSimpleName());
        }
        else {
            log.info(">>>>>>>>>>type = void");
        }
    }

    private Method createMethodSignature(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
}
