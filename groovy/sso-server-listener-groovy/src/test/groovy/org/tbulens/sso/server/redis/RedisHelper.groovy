package org.tbulens.sso.server.redis


class RedisHelper {
    RedisUtil redisUtil

    void clean() {
        Set<String> keyList = redisUtil.redisTemplate.keys("*");
        for(String key:keyList){
            redisUtil.delete(key)
        }
    }
}
