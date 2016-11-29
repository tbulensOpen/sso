package org.tbulens.sso.server

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext
import org.tbulens.sso.client.login.LoginRequest

@WithGMock
class LoginRequestProcessorTest {
    LoginRequest loginRequest
    LoginRequestProcessor processor

    @Before
    void setUp() {
        loginRequest = new LoginRequest(userId: "userId", sessionId: "sessionId")
        processor = new LoginRequestProcessor()
    }

    @Test
    void login() {
        String rt = processor.login(loginRequest.toJson())
        assert rt == loginRequest.userId + ":" + loginRequest.sessionId
    }
}
