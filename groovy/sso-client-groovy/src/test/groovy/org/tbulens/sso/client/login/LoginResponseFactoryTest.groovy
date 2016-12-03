package org.tbulens.sso.client.login

import org.junit.Test


class LoginResponseFactoryTest {

    @Test
    void create() {
        LoginResponse expectedResponse = new LoginResponseBuilder().build()
        String responseJson = expectedResponse.toJson()
        LoginResponse actualResponse = new LoginResponseFactory().create(responseJson)

        assert actualResponse == expectedResponse
    }
}
