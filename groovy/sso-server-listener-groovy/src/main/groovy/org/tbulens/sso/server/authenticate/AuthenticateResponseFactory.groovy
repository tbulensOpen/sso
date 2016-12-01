package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.server.login.LoginTicket

@Component
class AuthenticateResponseFactory {

    AuthenticateResponse create(LoginTicket, int status) {
        new AuthenticateResponse()
    }
}
