package org.tbulens.sso.client.login

import org.tbulens.sso.client.SsoJwtToken


class LoginResponseBuilder {
    String secureCookieId = "secureCookieId"
    String userId = "userId"
    String originalServiceUrl = "http://localhost:8080/testapp"
    int statusId = LoginResponse.VALID_REQUEST
    String requestTicket = "RT_requestTicket"

    LoginResponse build() {
        LoginResponse loginResponse = new LoginResponse()
        SsoJwtToken ssoJwtToken = new SsoJwtToken(secureCookieId: secureCookieId, userId: userId, requestTicket: requestTicket)
        loginResponse.ssoJwtToken = ssoJwtToken
        loginResponse.originalServiceUrl = originalServiceUrl
        loginResponse.statusId = statusId
        loginResponse
    }
}
