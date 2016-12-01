package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.util.TicketGenerator
import org.tbulens.sso.server.login.LoginTicket

@Component
class AuthenticateResponseFactory {
    TicketGenerator ticketGenerator = new TicketGenerator()

    protected AuthenticateResponse create(LoginTicket loginTicket, int statusId, Map<String, Object> authenticateRequestMap) {
        AuthenticateResponse response = new AuthenticateResponse()
        response.sessionId = loginTicket.sessionId
        response.secureCookieId = loginTicket.secureCookieId
        response.statusId = statusId
        response.originalServiceUrl = authenticateRequestMap.originalServiceUrl

        response.requestTicket =  statusId != AuthenticateResponse.AUTHENTICATED ?: ticketGenerator.generateRequestTicket()
        response
    }
}
