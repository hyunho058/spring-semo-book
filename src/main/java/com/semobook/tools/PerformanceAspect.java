package com.semobook.tools;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PerformanceAspect {

//    @Around("execution(* com.semobook..*.UserController.*(..))")  //포인트컷 표현식을 이용한 방법
    @Around("@annotation(com.semobook.tools.PerformanceCheck)") //annotation을 이용한 방법
    public Object calculatePerformanceTime(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            log.info("{} ms", end - start);
        } catch (Throwable throwable) {
            log.info("Exception");
            throwable.printStackTrace();
        }
        return result;
    }
}
