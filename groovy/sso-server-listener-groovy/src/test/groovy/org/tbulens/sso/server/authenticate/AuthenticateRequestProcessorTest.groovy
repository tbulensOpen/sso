package org.tbulens.sso.server.authenticate

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.SsoJwtToken
import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateRequestBuilder
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketBuilder
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.redis.RedisHelper
import org.tbulens.sso.redis.RedisUtil

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ["classpath:contexts/sso-server-application-context.xml"])
class AuthenticateRequestProcessorTest {

    @Autowired AuthenticateRequestProcessor authenticateRequestProcessor
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    AuthenticateRequest authenticateRequest
    LoginTicket loginTicket
    JsonUtil jsonUtil = new JsonUtil()
    RedisHelper redisHelper

    @Before
    void setUp() {
        authenticateRequest = new AuthenticateRequestBuilder().build()
        loginTicket = new LoginTicketBuilder().withAuthenticateRequest(authenticateRequest).build()
        redisHelper = new RedisHelper(redisUtil: redisUtil)
        redisHelper.clean()
    }

    @Test
    void process_valid_oneApp() {
        redisUtil.push(loginTicket.ssoJwtToken.secureCookieId, loginTicket.toJson())

        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson, Map.class)

        assert authenticateResponseMap.ssoJwtToken.secureCookieId == authenticateRequest.secureCookieId
        assert authenticateResponseMap.ssoJwtToken.requestTicket != authenticateRequest.requestTicket
        assert authenticateResponseMap.ssoJwtToken.requestTicket.contains("RT_")
        assert authenticateResponseMap.statusId == AuthenticateResponse.AUTHENTICATED
        assert authenticateResponseMap.originalServiceUrl == authenticateRequest.originalServiceUrl

        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(loginTicket.ssoJwtToken.secureCookieId)
//        assert authenticateResponseMap.ssoJwtToken.requestTicket == loginTicketUpdated.services[authenticateRequest.originalServiceUrl]
    }

    @Test
    void process_valid_secondApp() {
        redisUtil.push(loginTicket.ssoJwtToken.secureCookieId, loginTicket.toJson())

        authenticateRequest.requestTicket = null
        authenticateRequest.originalServiceUrl = authenticateRequest.originalServiceUrl + "2"
        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson, Map.class)

        assert authenticateResponseMap.statusId == AuthenticateResponse.AUTHENTICATED
        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(loginTicket.ssoJwtToken.secureCookieId)

        assert authenticateResponseMap.originalServiceUrl == authenticateRequest.originalServiceUrl
//        assert  loginTicketUpdated.services[authenticateRequest.originalServiceUrl] == authenticateResponseMap.requestTicket
    }

    @Ignore
    @Test
    void process_invalid_unauthorized() {
        redisUtil.push(loginTicket.ssoJwtToken.secureCookieId, loginTicket.toJson())

        authenticateRequest.requestTicket = "invalidRequestTicket"
        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson, Map.class)

        assert authenticateResponseMap.statusId == AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION
        assert !loginTicketFactory.createFromSecureCookie(loginTicket.ssoJwtToken.secureCookieId)
    }

    @Test
    void process_invalid_expired() {
        loginTicket.expiredTime = (new Date()) - 1
        redisUtil.push(loginTicket.ssoJwtToken.secureCookieId, loginTicket.toJson())

        authenticateRequest.requestTicket = "invalidRequestTicket"
        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson, Map.class)

        assert authenticateResponseMap.statusId == AuthenticateResponse.TICKET_EXPIRED
        assert !loginTicketFactory.createFromSecureCookie(loginTicket.ssoJwtToken.secureCookieId)
    }
}
