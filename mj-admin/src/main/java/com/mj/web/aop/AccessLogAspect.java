package com.mj.web.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;

/**
 * @Author anyang
 * @CreateTime 2023/5/12
 * @Des 接口访问切面，记录访问日志
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class AccessLogAspect {
    @Around("execution(* com.mj.iot.controller.*.*(..))")
    public Object controller(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant startTime = Instant.now();
        Object result = joinPoint.proceed();
        Instant endTime = Instant.now();
        String methodName = joinPoint.getSignature().getName();
        long duration = Duration.between(startTime, endTime).toMillis();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = request.getRemoteAddr();
        Object[] args = joinPoint.getArgs();
        log.info("接口名称：{}，接口参数：{}，接口返回值：{}，接口耗时：{}，客户端ip：{}", methodName, args, JSON.toJSONString(result), duration, ipAddress);
        return result;
    }
}
