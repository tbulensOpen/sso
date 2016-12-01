package org.tbulens.sso.server.authenticate

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateRequestBuilder
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketBuilder
import org.tbulens.sso.server.login.LoginTicketFactory
import org.tbulens.sso.server.redis.RedisUtil

import java.text.SimpleDateFormat

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ["classpath:contexts/sso-server-application-context.xml"])
class AuthenticateRequestProcessorTest {

    @Autowired AuthenticateRequestProcessor authenticateRequestProcessor
    @Autowired RedisUtil redisUtil
    @Autowired LoginTicketFactory loginTicketFactory
    AuthenticateRequest authenticateRequest
    LoginTicket loginTicket
    JsonUtil jsonUtil = new JsonUtil()

    @Before
    void setUp() {
        authenticateRequest = new AuthenticateRequestBuilder().build()
        loginTicket = new LoginTicketBuilder().withAuthenticateRequest(authenticateRequest).build()
    }

    @Test
    void process_valid_oneApp() {
        redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())

        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson)

        assert authenticateResponseMap.secureCookieId == authenticateRequest.secureCookieId
        assert authenticateResponseMap.sessionId == authenticateRequest.sessionId
        assert authenticateResponseMap.requestTicket != authenticateRequest.requestTicket
        assert authenticateResponseMap.requestTicket.contains("RT_")
        assert authenticateResponseMap.statusId == AuthenticateResponse.AUTHENTICATED
        assert authenticateResponseMap.originalServiceUrl == authenticateRequest.originalServiceUrl

        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(loginTicket.secureCookieId)
        assert authenticateResponseMap.requestTicket == loginTicketUpdated.services[authenticateRequest.originalServiceUrl]
    }

    @Test
    void process_valid_secondApp() {
        redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())

        authenticateRequest.requestTicket = null
        authenticateRequest.originalServiceUrl = authenticateRequest.originalServiceUrl + "2"
        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson)

        assert authenticateResponseMap.statusId == AuthenticateResponse.AUTHENTICATED
        LoginTicket loginTicketUpdated = loginTicketFactory.createFromSecureCookie(loginTicket.secureCookieId)

        assert authenticateResponseMap.originalServiceUrl == authenticateRequest.originalServiceUrl
        assert  loginTicketUpdated.services[authenticateRequest.originalServiceUrl] == authenticateResponseMap.requestTicket


    }

    @Test
    void process_invalid_unauthorized() {
        redisUtil.push(loginTicket.secureCookieId, loginTicket.toJson())

        authenticateRequest.requestTicket = "invalidRequestTicket"
        String authenticateResponseJson = authenticateRequestProcessor.process(authenticateRequest.toJson())

        Map<String, Object> authenticateResponseMap = jsonUtil.fromJson(authenticateResponseJson)

        assert authenticateResponseMap.statusId == AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION
        assert !loginTicketFactory.createFromSecureCookie(loginTicket.secureCookieId)

    }
}
