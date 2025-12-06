package com.jhonatapers.aop.executor;

import org.aspectj.lang.ProceedingJoinPoint;

@FunctionalInterface
public interface Executor<J extends ProceedingJoinPoint> {

    void execute(J joinPoint);

}
