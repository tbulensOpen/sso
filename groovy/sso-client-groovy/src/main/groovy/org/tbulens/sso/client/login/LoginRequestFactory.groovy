package org.tbulens.sso.client.login

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class LoginRequestFactory {

    LoginRequest create(HttpServletRequest request) {
        String sessionId = request.session.id
        String userId = request.getParameter("userId")

        LoginRequest loginRequest = new LoginRequest()
        loginRequest.userId = userId
        loginRequest.sessionId = sessionId

        String encodedServiceUrl = request.getParameter("service")
        loginRequest.originalServiceUrl = encodedServiceUrl ? decodeServiceUrl(encodedServiceUrl) : null
        loginRequest
    }

    private String decodeServiceUrl(String service) {
        URLDecoder.decode(service, "UTF-16")

    }
}
