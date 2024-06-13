package com.sparta.newspeed.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    /*
    - 모든 API(Controller)가 호출될 때, Request 정보(Request URL, HTTP Method)를
    **@Slf4J Logback** 라이브러리를  활용하여 Log로 출력해주세요.
    - 컨트롤러 마다 로그를 출력하는 코드를 추가하는것이 아닌, AOP로 구현해야만 합니다.
    */

    // 포인트컷 표현식 수정
    @Pointcut("execution(* com.sparta.newspeed..*Controller.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object logBeforeControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Map<String, Object> params = new HashMap<>();

        try {
            String decodedURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");

            params.put("request_uri", decodedURI);
            params.put("http_method", request.getMethod());
        } catch (Exception e) {
            log.error("LoggerAspect error", e);
        }

        log.info("Request URL : {}", params.get("request_uri"));
        log.info("HTTP Method : {}", params.get("http_method"));

        Object result = joinPoint.proceed();

        return result;
    }
}