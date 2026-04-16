package com.example.System.Configuration.RateLimiter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GlobalRateLimitFilter extends OncePerRequestFilter {

    private final SlidingWindowRateLimiter rateLimiter;
    private final SecurityUtils securityUtils;

    private static final int DEFAULT_LIMIT = 50;
    private static final int DEFAULT_WINDOW = 60;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String endpoint = request.getRequestURI();
        String userId = SecurityUtils.getCurrentUserId();

        String key = (userId != null)
                ? "user:" + userId + ":" + endpoint
                : "ip:" + request.getRemoteAddr() + ":" + endpoint;

        boolean allowed = rateLimiter.isAllowed(key, DEFAULT_LIMIT, DEFAULT_WINDOW);

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Rate limit exceeded");
        }

        filterChain.doFilter(request, response);
    }

}