package org.tbulens.sso.loginweb.controller

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpSession
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.login.LoginSender
import org.tbulens.sso.loginweb.login.LoginController
import org.tbulens.sso.client.login.LoginRequestFactory

@WithGMock
class LoginControllerTest {
    LoginController loginController
    LoginSender mockLoginSender
    MockHttpServletRequest mockRequest
    MockHttpServletResponse mockResponse
    MockHttpSession mockSession
    LoginRequest loginRequest
    LoginResponse loginResponse


    @Before
    void setUp() {
        String serviceUrl = "http://localhost:8080/testapp?arg1=value1&arg2=value2"
        String encodedUrl = URLEncoder.encode(serviceUrl, "UTF-16")
        loginRequest = new LoginRequest(sessionId: "sessionId1", userId: "userA", originalServiceUrl: serviceUrl)

        mockResponse = new MockHttpServletResponse()
        mockRequest = new MockHttpServletRequest()
        mockRequest.addParameter("service", encodedUrl)
        mockRequest.addParameter("userId", "userA")

        mockSession = new MockHttpSession(null, loginRequest.sessionId)
        mockRequest.setSession(mockSession)

        mockLoginSender = mock(LoginSender)

        loginController = new LoginController(cookieDomain: "localhost", loginRequestFactory: new LoginRequestFactory())
        loginController.loginSender = mockLoginSender

    }

    @Test
    void login_success() {
        loginResponse = new LoginResponse(statusId: LoginResponse.VALID_REQUEST, secureCookieId: "secureId")
        mockLoginSender.send(loginRequest).returns(loginResponse)

        play {
            loginController.login(mockRequest, mockResponse)
            assert mockResponse.getCookie("ssoCookie").value == loginResponse.secureCookieId
        }
    }
}
