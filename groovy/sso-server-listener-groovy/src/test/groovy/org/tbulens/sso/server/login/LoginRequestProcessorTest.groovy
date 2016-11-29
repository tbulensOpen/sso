package org.tbulens.sso.server.login

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.server.login.LoginRequestProcessor

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class LoginRequestProcessorTest {

    @Autowired LoginRequestProcessor loginRequestProcessor
    LoginRequest loginRequest

    @Before
    void setUp() {
        loginRequest = new LoginRequest(userId: "userId", sessionId: "sessionId")
    }

    @Test
    void login() {
        String rt = loginRequestProcessor.login(loginRequest.toJson())
        assert rt == loginRequest.userId + ":" + loginRequest.sessionId
    }
}
