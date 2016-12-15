package org.tbulens.sso.client.authenticate

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginRequestBuilder
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.login.LoginSender
import org.tbulens.sso.redis.RedisHelper
import org.tbulens.sso.redis.RedisUtil

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/test-client-context.xml"])
class AuthenticateSenderImplTest {

    @Autowired AuthenticateSender authenticateSender
    @Autowired LoginSender loginSender
    @Autowired RedisUtil redisUtil
    AuthenticateRequest authenticateRequest
    RedisHelper redisHelper

    @Before
    void setUp() {
        authenticateRequest = new AuthenticateRequestBuilder().build()
        redisHelper = new RedisHelper(redisUtil: redisUtil)
        redisHelper.clean()
    }

    @Ignore
    @Test
    void send() {
        LoginRequest loginRequest = new LoginRequestBuilder().build()
        LoginResponse loginResponse = loginSender.send(loginRequest)

        authenticateRequest = new AuthenticateRequestBuilder().build()
        authenticateRequest.secureCookieId = loginResponse.secureCookieId
        authenticateRequest.requestTicket = loginResponse.requestTicket
        authenticateRequest.originalServiceUrl = loginResponse.originalServiceUrl

        AuthenticateResponse response = authenticateSender.send(authenticateRequest)
        assert response.statusId == AuthenticateResponse.AUTHENTICATED
        assert response.requestTicket != loginResponse.requestTicket
    }
}
