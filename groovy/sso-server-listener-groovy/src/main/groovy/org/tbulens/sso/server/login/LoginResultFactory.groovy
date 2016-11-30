package org.tbulens.sso.server.login

import org.springframework.stereotype.Component

@Component
class LoginResultFactory {

    LoginResult create(LoginTicket loginTicket) {
        new LoginResult(secureCookieId: loginTicket.secureCookieId, userId: loginTicket.userId, sessionId: loginTicket.sessionId, originalServiceUrl: loginTicket.originalServiceUrl)
    }
}
