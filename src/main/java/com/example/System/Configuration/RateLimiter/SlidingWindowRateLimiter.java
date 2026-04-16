package com.example.System.Configuration.RateLimiter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SlidingWindowRateLimiter {

    private final StringRedisTemplate redisTemplate;

    public boolean isAllowed(String key, int limit, int windowSeconds) {

        String redisKey = "rate_limit:" + key;
        long now = System.currentTimeMillis();
        long windowStart = now - (windowSeconds * 1000L);

        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();

        zSet.removeRangeByScore(redisKey, 0, windowStart);

        Long count = zSet.zCard(redisKey);

        if (count != null && count >= limit) {
            return false;
        }

        zSet.add(redisKey, String.valueOf(now), now);

        redisTemplate.expire(redisKey, Duration.ofSeconds(windowSeconds));

        return true;
    }
}