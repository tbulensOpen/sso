package org.tbulens.sso.loginweb.controller

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.tbulens.sso.loginweb.login.LoginController
import org.tbulens.sso.loginweb.login.LoginSsoSender

@WithGMock
class LoginControllerTest {
    LoginController loginController
    LoginSsoSender mockLoginSsoSender

    @Before
    void setUp() {
        mockLoginSsoSender = mock(LoginSsoSender)
        loginController = new LoginController()
        loginController.loginSsoSender = mockLoginSsoSender
    }

    @Test
    void login() {

    }
}
