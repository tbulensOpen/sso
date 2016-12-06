package org.tbulens.sso.redis

import org.tbulens.sso.redis.RedisUtil


class RedisHelper {
    RedisUtil redisUtil

    void clean() {
        Set<String> keyList = redisUtil.redisTemplate.keys("*");
        for(String key:keyList){
            redisUtil.delete(key)
        }
    }
}
