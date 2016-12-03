package org.tbulens.sso.loginweb.controller

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpSession
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.loginweb.login.LoginController
import org.tbulens.sso.loginweb.login.LoginRequestFactory
import org.tbulens.sso.loginweb.login.LoginSsoSender

import javax.servlet.http.HttpServletRequest

@WithGMock
class LoginControllerTest {
    LoginController loginController
    LoginSsoSender mockLoginSsoSender
    MockHttpServletRequest mockRequest
    MockHttpSession mockSession
    LoginRequest loginRequest
    LoginResponse loginResponse


    @Before
    void setUp() {
        String serviceUrl = "http://localhost:8080/testapp?arg1=value1&arg2=value2"
        String encodedUrl = URLEncoder.encode(serviceUrl, "UTF-16")
        loginRequest = new LoginRequest(sessionId: "sessionId1", userId: "userA", originalServiceUrl: serviceUrl)

        mockRequest = new MockHttpServletRequest()
        mockRequest.addParameter("service", encodedUrl)
        mockRequest.addParameter("userId", "userA")

        mockSession = new MockHttpSession(null, loginRequest.sessionId)
        mockRequest.setSession(mockSession)

        mockLoginSsoSender = mock(LoginSsoSender)

        loginController = new LoginController(loginRequestFactory: new LoginRequestFactory())
        loginController.loginSsoSender = mockLoginSsoSender

    }

    @Test
    void login_success() {
        loginResponse = new LoginResponse(statusId: LoginResponse.VALID_REQUEST)
        mockLoginSsoSender.send(loginRequest).returns(loginResponse)

        play {
            loginController.login(mockRequest, null)
        }
    }
}
