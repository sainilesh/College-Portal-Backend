package com.example.System.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClearRedisData {

    private final RedisTemplate<String, Object> redisTemplate;

    @EventListener(ContextClosedEvent.class)
    public void clearCache() {

        System.out.println("Clearing Redis cache on shutdown...");

        redisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushAll();
    }
}