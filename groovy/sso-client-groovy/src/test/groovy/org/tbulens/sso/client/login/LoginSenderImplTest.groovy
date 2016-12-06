package org.tbulens.sso.client.login

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.redis.RedisHelper
import org.tbulens.sso.redis.RedisUtil

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/test-client-context.xml"])
class LoginSenderImplTest {
    @Autowired LoginSender loginSender
    @Autowired RedisUtil redisUtil
    RedisHelper redisHelper

    @Before
    void setUp() {
        redisHelper = new RedisHelper(redisUtil: redisUtil)
        redisHelper.clean()
    }

    @Ignore
    @Test
    void send() {
        LoginRequest loginRequest = new LoginRequestBuilder().build()
        LoginResponse loginResponse = loginSender.send(loginRequest)

        assert loginResponse.statusId == LoginResponse.VALID_REQUEST
        assert loginResponse.secureCookieId
        assert loginResponse.requestTicket
        assert loginResponse.sessionId == loginRequest.sessionId
        assert loginResponse.originalServiceUrl == loginRequest.originalServiceUrl
    }

    @Ignore
    @Test
    void send_alreadyLoggedIn() {
        LoginRequest loginRequest = new LoginRequestBuilder().build()
        LoginResponse loginResponse1 = loginSender.send(loginRequest)
        LoginResponse loginResponse2 = loginSender.send(loginRequest)

        assert loginResponse2.statusId == LoginResponse.USER_ALREADY_LOGGED_IN
        assert !loginResponse2.requestTicket
    }
}
