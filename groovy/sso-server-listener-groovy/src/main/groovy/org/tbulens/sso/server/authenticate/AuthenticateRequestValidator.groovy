package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.server.login.LoginTicket


@Component
class AuthenticateRequestValidator {

    protected int validate(Map<String, Object> authenticateRequestMap, LoginTicket loginTicket) {
        0
    }
}
