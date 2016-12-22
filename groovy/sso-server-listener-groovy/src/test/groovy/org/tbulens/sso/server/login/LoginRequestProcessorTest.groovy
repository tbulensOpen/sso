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
import org.tbulens.sso.client.util.JsonUtil
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
        def loginResponse = new JsonUtil().fromJson(loginTicketResult, Map.class)

        assert loginResponse.originalServiceUrl == loginRequest.originalServiceUrl
        assert loginResponse.ssoJwtToken.secureCookieId
        assert loginResponse.ssoJwtToken.userId == loginRequest.userId
        assert loginResponse.statusId == LoginResponse.VALID_REQUEST
//        assert loginResponse.ssoJwtToken.requestTicket.contains("RT_")

        assertLoginTicket(loginResponse.ssoJwtToken.secureCookieId)
    }

    @Test
    void login_invalid_userAlreadyLogin() {
       loginRequestProcessor.login(loginRequest.toJson())

        String loginResponse = loginRequestProcessor.login(loginRequest.toJson())
        Map<String, Object> loginResponseMap = new JsonUtil().fromJson(loginResponse, Map.class)

        assert loginResponseMap.statusId == LoginResponse.USER_ALREADY_LOGGED_IN
        assert loginResponseMap.ssoJwtToken.userId == loginRequest.userId
        assert loginResponseMap.originalServiceUrl == loginRequest.originalServiceUrl
        assert !loginResponseMap.ssoJwtToken.requestTicket
        assert !loginResponseMap.ssoJwtToken.secureCookieId
    }

    private void assertLoginTicket(String secureCookieId) {
        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(secureCookieId)

        assert loginTicketUpdated.ssoJwtToken.secureCookieId
        assert loginTicketUpdated.ssoJwtToken.userId == loginRequest.userId
        assert loginTicketUpdated.expiredTime

        assert loginTicketUpdated.services[loginRequest.originalServiceUrl]
    }
}
