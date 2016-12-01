package org.tbulens.sso.server.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

@Component
class RedisUtil {
    @Autowired StringRedisTemplate redisTemplate

    void push(String key, String value) {
        ValueOperations values = redisTemplate.opsForValue()
        values.set(key, value);
    }

    String get(String key) {
        ValueOperations values = redisTemplate.opsForValue()
        values.get(key)
    }

    void delete(String key) {
        redisTemplate.hasKey(key) ?  redisTemplate.delete(key) : null
    }
}
