package com.motivation.mojaty.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface Aop {

    void before(JoinPoint joinPoint);
    void after(JoinPoint joinPoint, Object object);
}
