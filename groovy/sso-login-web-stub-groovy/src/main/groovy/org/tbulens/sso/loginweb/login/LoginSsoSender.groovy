package org.tbulens.sso.loginweb.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.login.LoginSender

@Component
class LoginSsoSender {
    @Autowired LoginSender loginSender

    protected LoginResponse send(LoginRequest loginRequest) {
        LoginResponse loginResponse = loginSender.send(loginRequest)

        if (loginResponse.statusId != LoginResponse.VALID_REQUEST) {
            // sso server rejected login attempt, have user close browser, try again.
            // log failure
        }
        loginResponse
    }
}
