package org.tbulens.sso.server.login

import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse

@Component
class LoginRequestValidator {

    protected int validate(def loginRequestMap, LoginTicket loginTicket) {
        String userId = loginRequestMap.userId

        if (checkBadRequest(loginRequestMap, userId)) return LoginResponse.BAD_REQUEST
        if (loginTicket) return LoginResponse.USER_ALREADY_LOGGED_IN
        LoginResponse.VALID_REQUEST
    }

    private boolean checkBadRequest(def loginRequestMap, String userId) {
        String originalServiceUrl = loginRequestMap.originalServiceUrl

        !userId || !originalServiceUrl
    }
}
