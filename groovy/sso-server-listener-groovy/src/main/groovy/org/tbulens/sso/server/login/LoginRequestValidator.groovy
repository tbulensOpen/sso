package org.tbulens.sso.server.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.redis.RedisUtil

@Component
class LoginRequestValidator {
    @Autowired RedisUtil redisUtil
    JsonUtil jsonUtil = new JsonUtil()

    protected int validate(def loginRequestMap, LoginTicket loginTicket) {
        String userId = loginRequestMap.userId

        if (checkBadRequest(loginRequestMap, userId)) return LoginResponse.BAD_REQUEST
        if (loginTicket) return LoginResponse.USER_ALREADY_LOGGED_IN
        LoginResponse.VALID_REQUEST
    }

    private boolean checkBadRequest(def loginRequestMap, String userId) {
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl

        !userId || !sessionId || !originalServiceUrl
    }
}
