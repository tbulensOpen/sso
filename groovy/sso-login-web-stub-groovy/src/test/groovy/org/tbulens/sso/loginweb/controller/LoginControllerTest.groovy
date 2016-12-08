package org.tbulens.sso.loginweb.controller

import org.gmock.WithGMock
import org.junit.Before
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
import org.tbulens.sso.loginweb.login.CredentialFactory
import org.tbulens.sso.loginweb.login.LoginController
import org.tbulens.sso.client.login.LoginRequestFactory
import org.tbulens.sso.loginweb.login.LoginForm
import org.tbulens.sso.loginweb.login.LoginValidator

import javax.xml.bind.DataBindingException

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
        String serviceUrl = "http://localhost:8080/testapp?arg1=value1&arg2=value2"
        encodedUrl = URLEncoder.encode(serviceUrl, "UTF-16")
        loginRequest = new LoginRequest(sessionId: "sessionId1", userId: "123456", originalServiceUrl: serviceUrl)

        mockResponse = new MockHttpServletResponse()
        mockRequest = new MockHttpServletRequest()
        mockRequest.addParameter("service", encodedUrl)
        mockRequest.addParameter("username", loginRequest.userId)
        mockRequest.addParameter("password", "A#b1dddd")

        mockSession = new MockHttpSession(null, loginRequest.sessionId)
        mockRequest.setSession(mockSession)

        mockLoginSender = mock(LoginSender)

        loginController = new LoginController(cookieDomain: "localhost", loginRequestFactory: new LoginRequestFactory(),
                                              credentialFactory: new CredentialFactory(), loginValidator: new LoginValidator())
        loginController.loginSender = mockLoginSender

        loginForm = new LoginForm(username: "123456", password: "A#b1dddd")
    }

    @Test
    void login_get() {
        ModelMap model = new ModelMap()
        assert loginController.login(mockRequest, model) == "login"
        assert model.get("service") ==  encodedUrl
    }

    @Test
    void login_post_success() {
        loginResponse = new LoginResponse(statusId: LoginResponse.VALID_REQUEST, secureCookieId: "secureId")
        mockLoginSender.send(loginRequest).returns(loginResponse)

        play {
            loginController.login(mockRequest, mockResponse, loginForm, errors)
            assert mockResponse.getCookie(SsoCookieCreator.SSO_SESSION_ID).value == loginResponse.secureCookieId
        }
    }
}
