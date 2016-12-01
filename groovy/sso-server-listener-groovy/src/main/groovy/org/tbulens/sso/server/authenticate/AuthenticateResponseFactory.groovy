package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.TicketGenerator
import org.tbulens.sso.server.login.LoginTicket

@Component
class AuthenticateResponseFactory {
    TicketGenerator ticketGenerator = new TicketGenerator()

    protected AuthenticateResponse create(LoginTicket loginTicket, int statusId) {
        AuthenticateResponse response = new AuthenticateResponse()
        response.sessionId = loginTicket.sessionId
        response.secureCookieId = loginTicket.secureCookieId
        response.statusId = statusId
        response.requestTicket = ticketGenerator.generateRequestTicket()
        response
    }
}
