package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.login.LoginTicket


@Component
class AuthenticateRequestValidator {

    protected int validate(Map<String, Object> authenticateRequestMap, LoginTicket loginTicket) {
        String secureCookieId = authenticateRequestMap.secureCookieId
        String sessionId = authenticateRequestMap.sessionId
        String requestTicket = authenticateRequestMap.requestTicket
        String originalServiceUrl = authenticateRequestMap.originalServiceUrl

        if (!loginTicket) return AuthenticateResponse.NOT_AUTHENTICATED

        boolean allRequiredFields = secureCookieId && sessionId && originalServiceUrl
        if (!allRequiredFields) return AuthenticateResponse.BAD_REQUEST

        if (sessionId != loginTicket.sessionId) return AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION

        if (loginTicket.expiredTime <= new Date()) return AuthenticateResponse.TICKET_EXPIRED

        if (!requestTicket && loginTicket.originalServiceUrl != originalServiceUrl) return AuthenticateResponse.AUTHENTICATED

        if (loginTicket.requestTicket != requestTicket) return AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION

        AuthenticateResponse.AUTHENTICATED
    }
}
