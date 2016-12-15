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
import org.tbulens.sso.client.login.LoginRequestBuilder
import org.tbulens.sso.redis.RedisHelper
import org.tbulens.sso.redis.RedisUtil

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class LoginRequestProcessorTest {

    @Autowired LoginRequestProcessor loginRequestProcessor
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    RedisHelper redisHelper

    LoginRequest loginRequest
    JsonSlurper jsonSlurper = new JsonSlurper()

    @Before
    void setUp() {
        loginRequest = new LoginRequestBuilder().build()
        redisHelper = new RedisHelper(redisUtil: redisUtil)
        redisHelper.clean()
    }

    @Test
    void login_valid() {
        String loginTicketResult = loginRequestProcessor.login(loginRequest.toJson())
        def loginResponse = jsonSlurper.parseText(loginTicketResult)

        assert loginResponse.originalServiceUrl == loginRequest.originalServiceUrl
        assert loginResponse.secureCookieId
        assert loginResponse.userId == loginRequest.userId
        assert loginResponse.statusId == LoginResponse.VALID_REQUEST
        assert loginResponse.requestTicket.contains("RT_")

        assertLoginTicket(loginResponse.secureCookieId)
    }

    @Test
    void login_invalid_userAlreadyLogin() {
       loginRequestProcessor.login(loginRequest.toJson())

        String loginResponse = loginRequestProcessor.login(loginRequest.toJson())
        Map<String, Object> loginResponseMap = jsonSlurper.parseText(loginResponse)

        assert loginResponseMap.statusId == LoginResponse.USER_ALREADY_LOGGED_IN
        assert loginResponseMap.userId == loginRequest.userId
        assert loginResponseMap.originalServiceUrl == loginRequest.originalServiceUrl
        assert !loginResponseMap.requestTicket
        assert !loginResponseMap.secureCookieId
    }

    private void assertLoginTicket(String secureCookieId) {
        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(secureCookieId)

        assert loginTicketUpdated.secureCookieId
        assert loginTicketUpdated.userId == loginRequest.userId
        assert loginTicketUpdated.expiredTime

        assert loginTicketUpdated.services[loginRequest.originalServiceUrl]
    }
}
