package org.tbulens.sso.client.authenticate


class AuthenticateResponseBuilder {
    String secureCookieId = "secureCookieId"
    String sessionId = "sessionId"
    String requestTicket = "requestTicketId"
    int statusId = 0
    String originalServiceUrl = "http://localhost:8081/login?param1=value1&param2=value2"

    AuthenticateResponse build() {
        AuthenticateResponse response = new AuthenticateResponse()
        response.secureCookieId = secureCookieId
        response.sessionId = sessionId
        response.requestTicket = requestTicket
        response.statusId = statusId
        response.originalServiceUrl = originalServiceUrl
        response
    }
}
