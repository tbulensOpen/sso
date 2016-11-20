package org.tbulens.sso.client.model

import org.tbulens.sso.client.login.LoginRequest


class LoginRequestBuilder {
    String sessionId = "sessionId"
    String userId = "userA"

    LoginRequest build() {
        LoginRequest loginRequest = new LoginRequest()
        loginRequest.sessionId = sessionId
        loginRequest.userId = userId
        loginRequest
    }

}
