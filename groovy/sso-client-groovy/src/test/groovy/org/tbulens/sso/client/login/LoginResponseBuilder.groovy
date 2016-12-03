package org.tbulens.sso.client.login


class LoginResponseBuilder {
    String secureCookieId = "secureCookieId"
    String userId = "userId"
    String sessionId = "sessionId"
    String originalServiceUrl = "http://localhost:8080/testapp"
    int statusId = LoginResponse.VALID_REQUEST
    String requestTicket = "RT_requestTicket"

    LoginResponse build() {
        LoginResponse loginResponse = new LoginResponse()
        loginResponse.secureCookieId = secureCookieId
        loginResponse.userId = userId
        loginResponse.sessionId = sessionId
        loginResponse.originalServiceUrl = originalServiceUrl
        loginResponse.statusId = statusId
        loginResponse.requestTicket = requestTicket
        loginResponse
    }
}
