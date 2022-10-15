package com.motivation.mojaty.global.aop;

import org.aspectj.lang.JoinPoint;

public interface Aop {

    void before(JoinPoint joinPoint);
    void after(JoinPoint joinPoint);
}
