package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.login.LoginTicket


@Component
class AuthenticateRequestValidator {

    protected int validate(Map<String, Object> authenticateRequestMap, LoginTicket loginTicket) {
        String secureCookieId = authenticateRequestMap.secureCookieId
        String requestTicket = authenticateRequestMap.requestTicket
        String originalServiceUrl = authenticateRequestMap.originalServiceUrl

        if (!loginTicket) return AuthenticateResponse.NOT_AUTHENTICATED

        boolean allRequiredFields = secureCookieId && originalServiceUrl
        if (!allRequiredFields) return AuthenticateResponse.BAD_REQUEST

        if (loginTicket.isExpired()) return AuthenticateResponse.TICKET_EXPIRED

        String serviceRequestTicket = loginTicket.services[originalServiceUrl]

//        if (!requestTicket && !serviceRequestTicket) return AuthenticateResponse.AUTHENTICATED

//        if (serviceRequestTicket != requestTicket) return AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION

        AuthenticateResponse.AUTHENTICATED
    }
}
