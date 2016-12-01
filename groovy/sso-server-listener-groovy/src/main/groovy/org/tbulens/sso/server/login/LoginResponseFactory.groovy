package org.tbulens.sso.server.login

import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginResponse

@Component
class LoginResponseFactory {

    protected LoginResponse create(LoginTicket loginTicket, int status, Map<String, Object> loginRequestMap) {

        new LoginResponse(secureCookieId: loginTicket.secureCookieId, userId: loginTicket.userId,
                          sessionId: loginTicket.sessionId, originalServiceUrl: loginRequestMap.originalServiceUrl,
                          statusId: status, requestTicket: loginTicket.services[loginRequestMap.originalServiceUrl as String])
    }
}
