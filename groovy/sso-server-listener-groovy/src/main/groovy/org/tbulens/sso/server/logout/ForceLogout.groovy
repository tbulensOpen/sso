package org.tbulens.sso.server.logout

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.server.redis.RedisUtil

@Component
class ForceLogout {
    @Autowired RedisUtil redisUtil

    void logout(String secureCookieId) {
        redisUtil.delete(secureCookieId)
    }

}
