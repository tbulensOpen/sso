package org.tbulens.sso.server.login

import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.model.LoginRequestBuilder

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class LoginRequestProcessorTest {

    @Autowired LoginRequestProcessor loginRequestProcessor
    LoginRequest loginRequest
    JsonSlurper jsonSlurper = new JsonSlurper()

    @Before
    void setUp() {
        loginRequest = new LoginRequestBuilder().build()
    }

    @Test
    void login_valid() {
        String loginTicketResult = loginRequestProcessor.login(loginRequest.toJson())
        def result = jsonSlurper.parseText(loginTicketResult)

        assert result.originalServiceUrl == loginRequest.originalServiceUrl
        assert result.secureCookieId
        assert result.userId == loginRequest.userId
        assert result.sessionId == loginRequest.sessionId
        assert result.statusId == LoginResponse.VALID_REQUEST
    }

    @Test
    void login_invalid() {
        loginRequest.userId = null
        String loginTicketResult = loginRequestProcessor.login(loginRequest.toJson())
        def result = jsonSlurper.parseText(loginTicketResult)

        assert result.originalServiceUrl == loginRequest.originalServiceUrl
        assert result.secureCookieId
        assert result.userId == loginRequest.userId
        assert result.sessionId == loginRequest.sessionId
        assert result.statusId == LoginResponse.BAD_REQUEST
    }
}
