package org.tbulens.sso.client.authenticate

import org.tbulens.sso.client.SsoJwtToken


class AuthenticateResponseBuilder {
    String secureCookieId = "secureCookieId"
    String requestTicket = "requestTicketId"
    int statusId = 0
    String originalServiceUrl = "http://localhost:8081/login?param1=value1&param2=value2"

    AuthenticateResponse build() {
        AuthenticateResponse response = new AuthenticateResponse()
        SsoJwtToken ssoJwtToken = new SsoJwtToken(secureCookieId: secureCookieId, authorities: ['TEST_APP'], requestTicket: requestTicket)
        response.ssoJwtToken = ssoJwtToken
        response.statusId = statusId
        response.originalServiceUrl = originalServiceUrl
        response
    }
}
