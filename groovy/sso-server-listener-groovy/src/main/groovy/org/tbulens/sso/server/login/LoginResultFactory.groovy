package org.tbulens.sso.server.login

import org.springframework.stereotype.Component
import org.tbulens.sso.redis.login.LoginResult

@Component
class LoginResultFactory {

    LoginResult create(LoginTicket loginTicket) {
        new LoginResult(secureCookieId: loginTicket.secureCookieId, userId: loginTicket.userId, sessionId: loginTicket.sessionId, originalServiceUrl: loginTicket.originalServiceUrl)
    }
}
