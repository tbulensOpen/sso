package org.tbulens.sso.server.authenticate

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateRequestBuilder
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.util.JsonUtil
import org.tbulens.sso.server.login.LoginTicket
import org.tbulens.sso.server.login.LoginTicketBuilder


class AuthenticateRequestValidatorTest {
    AuthenticateRequestValidator validator
    LoginTicket loginTicket
    Map<String, Object> authenticateRequestMap
    JsonUtil jsonUtil = new JsonUtil()
    AuthenticateRequest authenticateRequest

    @Before
    void setUp() {
        authenticateRequest = new AuthenticateRequestBuilder().build()
        updateAuthRequestMap()

        loginTicket = new LoginTicketBuilder().withAuthenticateRequest(authenticateRequest).build()
        validator = new AuthenticateRequestValidator()
    }

    @Test
    void validate_valid_oneApp() {
        assert validator.validate(authenticateRequestMap, loginTicket) == AuthenticateResponse.AUTHENTICATED
    }

    @Test
    void validate_valid_secondApp() {
        authenticateRequest.requestTicket = ""
        authenticateRequest.originalServiceUrl = "http://localhost:8080/testApp2"
        updateAuthRequestMap()
        assert validator.validate(authenticateRequestMap, loginTicket) == AuthenticateResponse.AUTHENTICATED
    }

    @Test
    void validate_invalid_badRequest_required_fields_missing() {
        authenticateRequest.secureCookieId = ""
        authenticateRequest.originalServiceUrl = ""
        authenticateRequest.requestTicket = ""
        updateAuthRequestMap()
        assert validator.validate(authenticateRequestMap, loginTicket) == AuthenticateResponse.BAD_REQUEST
    }

    @Test
    void validate_invalid_expired() {
        loginTicket.expiredTime = new Date() - 1
        assert validator.validate(authenticateRequestMap, loginTicket) == AuthenticateResponse.TICKET_EXPIRED
    }

    @Ignore
    @Test
    void validate_invalid_requestToken() {
        authenticateRequest.requestTicket = "someOtherRequestTicket"
        updateAuthRequestMap()
        assert validator.validate(authenticateRequestMap, loginTicket) == AuthenticateResponse.NOT_AUTHORIZED_SECURITY_VIOLATION
    }

    @Test
    void validate_invalid_notLoggedIn() {
        assert validator.validate(authenticateRequestMap, null) == AuthenticateResponse.NOT_AUTHENTICATED
    }

    private void updateAuthRequestMap() {
        authenticateRequestMap = jsonUtil.fromJson(authenticateRequest.toJson(), Map.class)
    }
}
