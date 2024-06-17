package com.sparta.newspeed.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.sparta.newspeed..*Controller.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object logBeforeControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String requestUri = request.getRequestURI();
        String httpMethod = request.getMethod();

        log.info("Request URL : {} , HTTP Method : {}", requestUri, httpMethod);

        return joinPoint.proceed();
    }
}