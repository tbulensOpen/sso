package org.tbulens.sso.loginweb.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginSender

@Component
class LoginSsoSender {
    @Autowired LoginSender loginSender

    protected String send(LoginRequest loginRequest) {
        String requestTicket = loginSender.send(loginRequest)

        if (!requestTicket) {
            // sso server rejected login attempt, have user close browser, try again.
            // log failure
        }
        requestTicket
    }
}
