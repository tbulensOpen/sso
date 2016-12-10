package org.tbulens.sso.client.authenticate

import org.junit.Before
import org.junit.Test
import org.tbulens.sso.client.util.JsonUtil


class AuthResponseFactoryTest {
    AuthResponseFactory authResponseFactory
    AuthenticateResponse authenticateResponse
    JsonUtil jsonUtil = new JsonUtil()

    @Before
    void setUp() {
        authenticateResponse = new AuthenticateResponseBuilder().build()
        authResponseFactory = new AuthResponseFactory()
    }

    @Test
    void create() {
        String json = authenticateResponse.toJson()
        AuthenticateResponse actualResponse = jsonUtil.fromJson(json, AuthenticateResponse)
        assert actualResponse == authenticateResponse
    }
}
