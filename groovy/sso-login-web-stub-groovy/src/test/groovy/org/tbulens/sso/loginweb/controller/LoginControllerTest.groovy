package org.tbulens.sso.loginweb.controller

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockHttpSession
import org.springframework.ui.ModelMap
import org.springframework.validation.Errors
import org.springframework.validation.MapBindingResult
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.login.LoginSender
import org.tbulens.sso.common.util.SsoCookieCreator

import org.tbulens.sso.loginweb.login.LoginController
import org.tbulens.sso.client.login.LoginRequestFactory
import org.tbulens.sso.loginweb.login.LoginForm
import org.tbulens.sso.loginweb.login.LoginValidator

@WithGMock
class LoginControllerTest {
    LoginController loginController
    LoginSender mockLoginSender
    MockHttpServletRequest mockRequest
    MockHttpServletResponse mockResponse
    MockHttpSession mockSession
    LoginRequest loginRequest
    LoginResponse loginResponse
    String encodedUrl
    Errors errors = new MapBindingResult([:], '')
    LoginForm loginForm


    @Before
    void setUp() {
        String serviceUrl = "http://localhost:8081/login?arg1=value1&arg2=value2"
        encodedUrl = URLEncoder.encode(serviceUrl, "UTF-16")

        loginRequest = new LoginRequest(userId: "123456", originalServiceUrl: serviceUrl)

        mockResponse = new MockHttpServletResponse()
        mockRequest = new MockHttpServletRequest()
        mockRequest.setQueryString("service=" + encodedUrl)
        mockRequest.addParameter("username", loginRequest.userId)
        mockRequest.addParameter("password", "A#b1dddd")

        mockSession = new MockHttpSession(null, "sessionId")
        mockRequest.setSession(mockSession)

        mockLoginSender = mock(LoginSender)

        loginController = new LoginController(cookieDomain: "localhost", loginRequestFactory: new LoginRequestFactory(),
                                              loginValidator: new LoginValidator())
        loginController.loginSender = mockLoginSender

        loginForm = new LoginForm(username: "123456", password: "A#b1dddd")
    }

    @Test
    void login_get() {
        ModelMap model = new ModelMap()
        assert loginController.login(mockRequest, model) == "login"
        assert model.get("serviceUrl") ==  URLDecoder.decode(encodedUrl, "UTF-16")
    }

    @Ignore
    @Test
    void login_post_success() {
        ModelMap model = new ModelMap()
        model.put("service", encodedUrl)
        loginResponse = new LoginResponse(statusId: LoginResponse.VALID_REQUEST, secureCookieId: "secureId")
        mockLoginSender.send(loginRequest).returns(loginResponse)

        play {
            loginController.login(model, mockRequest, mockResponse, loginForm, errors)
            assert mockResponse.getCookie(SsoCookieCreator.SSO_SESSION_ID).value == loginResponse.secureCookieId
        }
    }
}
