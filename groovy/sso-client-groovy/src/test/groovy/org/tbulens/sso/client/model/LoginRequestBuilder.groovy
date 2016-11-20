package org.tbulens.sso.client.model

import org.tbulens.sso.client.login.LoginRequest


class LoginRequestBuilder {
    String sessionId = "sessionId"
    String ssoToken = "ssoToken"
    String userId = "userA"

    LoginRequest build() {
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.sessionId = sessionId
        loginRequest.ssoTicket = ssoToken
        loginRequest.userId = userId
        loginRequest
    }

}
