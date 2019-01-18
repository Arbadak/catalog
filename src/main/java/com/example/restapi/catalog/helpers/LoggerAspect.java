package com.example.restapi.catalog.helpers;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

    LoggingController loggingController = new LoggingController();

    protected Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public LoggerAspect() throws Exception {
        MBeanServer platformMbeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMbeanServer.registerMBean(loggingController, new ObjectName("CustomLogging", "name", "LogMethods"));
    }

    @Pointcut("execution(* com.example.restapi.catalog.service.implementation..*(..))")
    private void returnResult() {
    }

    @Pointcut("execution(* com.example.restapi.catalog..*(..))")
    private void probeMethod() {
    }


    @Around("probeMethod()")
    public Object watchTime(ProceedingJoinPoint joinPoint) {


        Object output = null;
        if (loggingController.isEnableLogging()) {
            long start = System.currentTimeMillis();
            logger.warn(ANSI_RED + "Исполнение метода:" + ANSI_BLUE + joinPoint.getSignature().toString() + ANSI_RED + " >>>**********************");
            output = null;
            for (Object object : joinPoint.getArgs()) {
                logger.info(ANSI_CYAN + "Пареметры: " + ANSI_GREEN + object);
            }

            try {
                output = joinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            long time = System.currentTimeMillis() - start;
            logger.warn(ANSI_RED + "Окончание метода :" + ANSI_BLUE + joinPoint.getSignature().toString() + ANSI_RESET + ", время: " + ANSI_YELLOW + time + " ms <<<" + ANSI_RESET);
            System.out.println();
        } else {
            try {
                output = joinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @AfterReturning(pointcut = "returnResult()", returning = "obj")
    public void printObject(Object obj) {

        if (loggingController.isShowReturnObjects()) {
            logger.warn(ANSI_CYAN + "**************** Начало обьекта ******************");
            logger.info(ANSI_PURPLE + obj.toString());
            logger.warn(ANSI_CYAN + "**************** Окончание обьекта ********************");
        }
    }
}