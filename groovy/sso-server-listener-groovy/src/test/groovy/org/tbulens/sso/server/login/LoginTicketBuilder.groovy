package org.tbulens.sso.server.login

import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.login.LoginRequest


class LoginTicketBuilder {
    String secureCookieId = "secureCookieId"
    String userId = "userA"
    Date createDate = new Date()
    Date lastAccessed = new Date()
    Date expiredTime = (new Date()) + 1
    Map<String, String> services = ["http://localhost:8080/testapp":"RT_requestTicket"]

    LoginTicket build() {
        LoginTicket loginTicket = new LoginTicket()
        loginTicket.secureCookieId = secureCookieId
        loginTicket.userId = userId
        loginTicket.createDate = createDate
        loginTicket.lastAccessed = lastAccessed
        loginTicket.expiredTime = expiredTime
        loginTicket.addServices(services)
        loginTicket
    }

    LoginTicketBuilder withAuthenticateRequest(AuthenticateRequest authenticateRequest) {
        secureCookieId = authenticateRequest.secureCookieId
        services.clear()
        services.put(authenticateRequest.originalServiceUrl, authenticateRequest.requestTicket)
        this
    }

    LoginTicketBuilder withServices(Map<String, String> services) {
        this.services.clear()
        this.services.putAll(services)
        this
    }
}
