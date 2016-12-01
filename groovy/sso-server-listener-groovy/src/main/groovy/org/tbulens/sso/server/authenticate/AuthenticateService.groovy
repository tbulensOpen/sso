package org.tbulens.sso.server.authenticate

import org.springframework.stereotype.Component
import org.tbulens.sso.client.authenticate.AuthenticateResponse

@Component
class AuthenticateService {

    AuthenticateResponse process(Map<String, Object> authenticateRequestMap) {
        new AuthenticateResponse()
    }
}
