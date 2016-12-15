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
        response.statusId = statusId
        response.originalServiceUrl = authenticateRequestMap.originalServiceUrl

        if (loginTicket) {
            response.secureCookieId = loginTicket.secureCookieId
            response.requestTicket = ticketGenerator.generateRequestTicket()
        }
        response
    }
}
