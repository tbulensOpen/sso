package org.tbulens.sso.client.authenticate


class AuthenticateRequestBuilder {
    String secureCookieId = "secureCookieId"
    String sessionId = "sessionId"
    String requestTicket = "RT_requestTicket"
    String originalServiceUrl = "http://localhost:8080/testApp"

    AuthenticateRequest build() {
        AuthenticateRequest request = new AuthenticateRequest()
        request.secureCookieId = secureCookieId
        request.sessionId = sessionId
        request.requestTicket = requestTicket
        request.originalServiceUrl = originalServiceUrl
        request
    }
}
