package com.example.System.Configuration.RateLimiter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final SlidingWindowRateLimiter rateLimiter;

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit)
            throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String endpoint = request.getRequestURI();

        String userId = SecurityUtils.getCurrentUserId();
        String key;

        if(userId != null){
            key = "user:" + userId + ":" + endpoint;
        }
        else{
            key = "ip" + request.getRemoteAddr() + ":" + endpoint;
        }

        int limit = rateLimit.limit() != 0 ? rateLimit.limit() : 5;
        int window = rateLimit.window() != 0 ? rateLimit.window() : 5;

        boolean allowed = rateLimiter.isAllowed(
                key,
                limit,
                window
        );

        if(!allowed) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Rate limit exceeded");
        }

        return joinPoint.proceed();
    }
}
