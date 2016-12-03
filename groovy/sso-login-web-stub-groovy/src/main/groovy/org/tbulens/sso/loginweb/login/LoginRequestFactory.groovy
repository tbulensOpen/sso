package org.tbulens.sso.loginweb.login

import org.springframework.stereotype.Component
import org.tbulens.sso.client.login.LoginRequest

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
