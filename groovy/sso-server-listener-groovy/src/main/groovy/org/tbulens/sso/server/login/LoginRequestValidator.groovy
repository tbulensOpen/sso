package org.tbulens.sso.server.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.redis.RedisUtil

@Component
class LoginRequestValidator {
    @Autowired RedisUtil redisUtil
    JsonUtil jsonUtil = new JsonUtil()

    final static int VALID_REQUEST = 0
    final static int BAD_REQUEST = 1
    final static int USER_ALREADY_LOGGED_IN = 2

    int validate(def loginRequestMap) {
        String userId = loginRequestMap.userId

        if (checkBadRequest(loginRequestMap, userId)) return BAD_REQUEST
        if (checkUserAlreadyLoggedIn(userId)) return USER_ALREADY_LOGGED_IN
        VALID_REQUEST
    }

    private boolean checkBadRequest(def loginRequestMap, String userId) {
        String sessionId = loginRequestMap.sessionId
        String originalServiceUrl = loginRequestMap.originalServiceUrl

        !userId || !sessionId || !originalServiceUrl
    }

    private boolean checkUserAlreadyLoggedIn(String userId) {
        redisUtil.get(userId)
    }
}
