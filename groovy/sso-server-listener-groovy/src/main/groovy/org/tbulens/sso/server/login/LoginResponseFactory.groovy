package org.tbulens.sso.server.login

import org.springframework.stereotype.Component
import org.tbulens.sso.client.SsoJwtToken
import org.tbulens.sso.client.login.LoginResponse

@Component
class LoginResponseFactory {

    protected LoginResponse create(LoginTicket loginTicket, int status, Map<String, Object> loginRequestMap) {

        SsoJwtToken ssoJwtToken = new SsoJwtToken(secureCookieId: loginTicket.ssoJwtToken.secureCookieId,
                                                  userId: loginTicket.ssoJwtToken.userId, requestTicket: null)
        new LoginResponse(ssoJwtToken: ssoJwtToken, statusId: status,
                          originalServiceUrl: loginRequestMap.originalServiceUrl)
    }
}
