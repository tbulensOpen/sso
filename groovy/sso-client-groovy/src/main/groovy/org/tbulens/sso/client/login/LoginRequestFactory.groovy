package org.tbulens.sso.client.login

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class LoginRequestFactory {

    LoginRequest create(HttpServletRequest request, String serviceUrl) {
        String sessionId = request.session.id
        String userId = request.getParameter("username")

        LoginRequest loginRequest = new LoginRequest()
        loginRequest.userId = userId
        loginRequest.sessionId = sessionId
        loginRequest.originalServiceUrl = serviceUrl
        loginRequest
    }
}
