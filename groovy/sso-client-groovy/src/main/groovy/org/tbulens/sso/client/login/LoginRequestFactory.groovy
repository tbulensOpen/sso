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
        loginRequest.originalServiceUrl = decodeServiceUrl(request.getParameter("service"))
        loginRequest
    }

    private String decodeServiceUrl(String service) {
        URLDecoder.decode(service, "UTF-16")

    }
}
