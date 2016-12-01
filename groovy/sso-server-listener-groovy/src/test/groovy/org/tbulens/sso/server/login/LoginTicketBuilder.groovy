package org.tbulens.sso.server.login

import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.login.LoginRequest


class LoginTicketBuilder {
    String secureCookieId = "secureCookieId"
    String sessionId = "sessionId"
    String userId = "userA"
    String originalServiceUrl = "http://localhost:8080/testapp"
    Date createDate = new Date()
    Date lastAccessed = new Date()
    Date expiredTime = new Date().plus(1)
    String requestTicket = "RT_requestTicket"

    LoginTicket build() {
        LoginTicket loginTicket = new LoginTicket()
        loginTicket.secureCookieId = secureCookieId
        loginTicket.sessionId = sessionId
        loginTicket.userId = userId
        loginTicket.originalServiceUrl = originalServiceUrl
        loginTicket.createDate = createDate
        loginTicket.lastAccessed = lastAccessed
        loginTicket.expiredTime = expiredTime
        loginTicket.requestTicket = requestTicket
        loginTicket
    }

    LoginTicketBuilder withAuthenticateRequest(AuthenticateRequest authenticateRequest) {
        secureCookieId = authenticateRequest.secureCookieId
        sessionId = authenticateRequest.sessionId
        requestTicket = authenticateRequest.requestTicket
        this
    }
}