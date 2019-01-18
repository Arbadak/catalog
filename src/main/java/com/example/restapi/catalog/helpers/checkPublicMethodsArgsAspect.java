package com.example.restapi.catalog.helpers;

import com.example.restapi.catalog.exceptions.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Аспект проверки сущевствования аругемнтов публичных методов
 */

@Component
@Aspect
public class checkPublicMethodsArgsAspect {


    @Pointcut("execution(public * com.example.restapi.catalog.service..*(..))")
    public void servicesMethods() {
    }

    @Pointcut("execution(public * com.example.restapi.catalog.controller..*(..))")
    public void controllersMethods() {
    }

    @Before("servicesMethods() || controllersMethods()")
    public void logBefore(JoinPoint joinPoint) {

        Object[] signatureArgs = joinPoint.getArgs();
        if (signatureArgs == null) {
            throw new NotFoundException("Аргумент не найден");
        }
    }
}
