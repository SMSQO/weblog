package com.weblog.aspect;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class HttpAspect {


    @Pointcut("execution(public * com.weblog.controller.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        val attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        val request = attributes.getRequest();

        String info = String.format(
                "[%6s] %s |> %s(%s)",
                request.getMethod(),
                request.getRequestURL(),
                joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName(),
                Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(","))
        );
        log.info(info);
    }
}
